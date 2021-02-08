package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.artifactsController.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.constant.TimeConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ArtifactsControllerV1ServiceImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceAPIV1Impl;
import com.qaprosoft.zafira.util.WaitUtil;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.File;


public class ArtifactsControllerTest extends ZafiraAPIBaseTest {

    private final static Logger LOGGER = Logger.getLogger(ArtifactsControllerTest.class);
    int testRunId = new TestRunServiceAPIImplV1().create();
    private final static int EXPECTED_COUNT_THREE = 3;
    private final static int EXPECTED_COUNT_ONE = 1;

    @AfterTest
    public void testFinishTestRun() {
        new TestRunServiceAPIImpl().deleteById(testRunId);
    }

    @Test
    public void testGetLogs() {
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        new ArtifactsControllerV1ServiceImpl().createLogRecord(testRunId, testId);
        GetLogsV1Method getLogsV1Method = new GetLogsV1Method(testRunId, testId);
        apiExecutor.expectStatus(getLogsV1Method, HTTPStatusCodeType.OK);
        Assert.assertTrue(WaitUtil.waitForLogFound(getLogsV1Method, EXPECTED_COUNT_ONE), "Log was not found!");
        apiExecutor.validateResponse(getLogsV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetScreenshots() {
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        String filePath = R.TESTDATA.get(ConfigConstant.SCREENSHOT_PATH_KEY);
        new ArtifactsControllerV1ServiceImpl().createScreenshot(testRunId, testId, filePath);
        GetScreenshotsV1Method getScreenshotsV1Method = new GetScreenshotsV1Method(testRunId, testId);
        apiExecutor.expectStatus(getScreenshotsV1Method, HTTPStatusCodeType.OK);
        Assert.assertTrue(WaitUtil.waitForScreenshotFound(getScreenshotsV1Method, EXPECTED_COUNT_ONE), "Screenshot was not found!");
        apiExecutor.validateResponse(getScreenshotsV1Method, JSONCompareMode.STRICT);
    }

    @Test
    public void testSendTestExecutionLogRecord() {
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        PostLogsV1Method postLogsV1Method = new PostLogsV1Method(testRunId, testId);
        apiExecutor.expectStatus(postLogsV1Method, HTTPStatusCodeType.ACCEPTED);
        apiExecutor.callApiMethod(postLogsV1Method);
    }

    @Test
    public void testSendOneScreenshot() {
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        String filePath = R.TESTDATA.get(ConfigConstant.SCREENSHOT_PATH_KEY);
        PostScreenshotsV1Method postScreenshotsV1Method = new PostScreenshotsV1Method(testRunId, testId, filePath);
        apiExecutor.expectStatus(postScreenshotsV1Method, HTTPStatusCodeType.CREATED);
        apiExecutor.callApiMethod(postScreenshotsV1Method);
    }

    @Test
    public void testSendOneScreenshotWithHeader() {
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        String filePath = R.TESTDATA.get(ConfigConstant.SCREENSHOT_PATH_KEY);
        PostScreenshotsV1Method postScreenshotsV1Method = new PostScreenshotsV1Method(testRunId, testId, filePath);
        postScreenshotsV1Method.setHeaders("x-zbr-screenshot-captured-at=3");
        apiExecutor.expectStatus(postScreenshotsV1Method, HTTPStatusCodeType.CREATED);
        apiExecutor.callApiMethod(postScreenshotsV1Method);
    }

    @Test
    public void testGetScreenshotsWithHeader() {
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        String filePath = R.TESTDATA.get(ConfigConstant.SCREENSHOT_PATH_KEY);
        new ArtifactsControllerV1ServiceImpl().createScreenshotWithHeader(testRunId, testId, filePath, TimeConstant.MILLISECONDS_DURATION);
        GetScreenshotsV1Method getScreenshotsV1Method = new GetScreenshotsV1Method(testRunId, testId);
        apiExecutor.expectStatus(getScreenshotsV1Method, HTTPStatusCodeType.OK);
        Assert.assertTrue(WaitUtil.waitForScreenshotFound(getScreenshotsV1Method, EXPECTED_COUNT_ONE), "Screenshot was not found!");
        String rs = apiExecutor.callApiMethod(getScreenshotsV1Method);
        String timestamp = JsonPath.from(rs).getString(JSONConstant.TIMESTAMP_RESULT_KEY);
        Assert.assertEquals(timestamp, String.valueOf(TimeConstant.MILLISECONDS_DURATION), "Timestamp is incorrect!");
        apiExecutor.validateResponse(getScreenshotsV1Method, JSONCompareMode.STRICT);
    }

    @Test
    public void testGetLogsWithThreeRecords() {
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        new ArtifactsControllerV1ServiceImpl().createLogRecord(testRunId, testId);
        new ArtifactsControllerV1ServiceImpl().createLogRecord(testRunId, testId);
        new ArtifactsControllerV1ServiceImpl().createLogRecord(testRunId, testId);
        GetLogsV1Method getLogsV1Method = new GetLogsV1Method(testRunId, testId);
        getLogsV1Method.setResponseTemplate("api/artifacts_controller/_get/rs_for_get3Logs.json");
        Assert.assertTrue(WaitUtil.waitForLogFound(getLogsV1Method, EXPECTED_COUNT_THREE), "Log was not found!");
        apiExecutor.validateResponse(getLogsV1Method, JSONCompareMode.STRICT);
    }

    @Test
    public void testGetScreenshotsWithThreeScreenshot() {
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        String filePath = R.TESTDATA.get(ConfigConstant.SCREENSHOT_PATH_KEY);
        new ArtifactsControllerV1ServiceImpl().createScreenshot(testRunId, testId, filePath);
        new ArtifactsControllerV1ServiceImpl().createScreenshot(testRunId, testId, filePath);
        new ArtifactsControllerV1ServiceImpl().createScreenshot(testRunId, testId, filePath);
        GetScreenshotsV1Method getScreenshotsV1Method = new GetScreenshotsV1Method(testRunId, testId);
        getScreenshotsV1Method.setResponseTemplate("api/artifacts_controller/_get/rs_for_get3Screenshots.json");
        Assert.assertTrue(WaitUtil.waitForScreenshotFound(getScreenshotsV1Method, EXPECTED_COUNT_THREE), "Screenshot was not found!");
        apiExecutor.validateResponse(getScreenshotsV1Method, JSONCompareMode.STRICT);
    }

    @Test
    public void testSendTestArtifact() {
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        File uploadFile = new File(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY));
        PostTestArtifactMethod postTestArtifact = new PostTestArtifactMethod(testRunId, testId, uploadFile);
        apiExecutor.expectStatus(postTestArtifact, HTTPStatusCodeType.CREATED);
        apiExecutor.callApiMethod(postTestArtifact);
        apiExecutor.validateResponse(postTestArtifact, JSONCompareMode.STRICT);
        WaitUtil.waitForTestArtifactFound(testRunId, testId, uploadFile.getName());
    }

    @Test
    public void testSendTestRunArtifact() {
        File uploadFile = new File(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY));
        PostTestRunArtifactMethod postTestRunArtifact = new PostTestRunArtifactMethod(testRunId, uploadFile);
        apiExecutor.expectStatus(postTestRunArtifact, HTTPStatusCodeType.CREATED);
        apiExecutor.callApiMethod(postTestRunArtifact);
        apiExecutor.validateResponse(postTestRunArtifact, JSONCompareMode.STRICT);
        WaitUtil.waitForTestRunArtifactFound(testRunId, uploadFile.getName());
    }

    @Test
    public void testSendTestArtifactReferences() {
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        PutTestArtifactReferencesMethod putTestArtifactReferencesMethod = new PutTestArtifactReferencesMethod(testRunId, testId);
        putTestArtifactReferencesMethod.addProperty(JSONConstant.VALUE_KEY, R.TESTDATA.get(ConfigConstant.ARTIFACT_REF_KEY));
        apiExecutor.expectStatus(putTestArtifactReferencesMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(putTestArtifactReferencesMethod);
        String result = new TestRunServiceAPIImplV1().getTestResultsByTestId(testRunId, testId);
        Assert.assertTrue(result.contains(R.TESTDATA.get(ConfigConstant.ARTIFACT_REF_KEY)),
                "Artifact reference  " + R.TESTDATA.get(ConfigConstant.ARTIFACT_REF_KEY) + " was not found!");
    }

    @Test
    public void testSendTestRunArtifactReferences() {
        PutTestRunArtifactReferencesMethod putTestRunArtifactReferencesMethod = new PutTestRunArtifactReferencesMethod(testRunId);
        putTestRunArtifactReferencesMethod.addProperty(JSONConstant.VALUE_KEY, R.TESTDATA.get(ConfigConstant.ARTIFACT_REF_KEY));
        putTestRunArtifactReferencesMethod.addProperty(JSONConstant.NAME_KEY, R.TESTDATA.get(ConfigConstant.ARTIFACT_REF_NAME_KEY));
        apiExecutor.expectStatus(putTestRunArtifactReferencesMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(putTestRunArtifactReferencesMethod);
        WaitUtil.waitForTestRunArtifactFound(testRunId, R.TESTDATA.get(ConfigConstant.ARTIFACT_REF_KEY));
    }

    @Test
    public void testSendTestLabels() {
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        PutTestLabelsMethod putTestLabels = new PutTestLabelsMethod(testRunId, testId);
        putTestLabels.addProperty(JSONConstant.LABEL_VALUE_KEY, R.TESTDATA.get(ConfigConstant.LABEL_VALUE_KEY));
        putTestLabels.addProperty(JSONConstant.LABEL_KEY_KEY, R.TESTDATA.get(ConfigConstant.LABEL_KEY_KEY));
        apiExecutor.expectStatus(putTestLabels, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(putTestLabels);
        String result = new TestRunServiceAPIImplV1().getTestResultsByTestId(testRunId, testId);
        Assert.assertTrue(result.contains(R.TESTDATA.get(ConfigConstant.LABEL_VALUE_KEY)),
                "Label  " + R.TESTDATA.get(ConfigConstant.LABEL_VALUE_KEY) + " was not found!");
        Assert.assertTrue(result.contains(R.TESTDATA.get(ConfigConstant.LABEL_KEY_KEY)),
                "Label with key: " + R.TESTDATA.get(ConfigConstant.LABEL_KEY_KEY) + " was not found!");
    }

    @Test
    public void testSendTestRunLabels() {
        PutTestRunLabelsMethod putTestRunLabelsMethod = new PutTestRunLabelsMethod(testRunId);
        putTestRunLabelsMethod.addProperty(JSONConstant.LABEL_VALUE_KEY, R.TESTDATA.get(ConfigConstant.LABEL_VALUE_KEY));
        putTestRunLabelsMethod.addProperty(JSONConstant.LABEL_KEY_KEY, R.TESTDATA.get(ConfigConstant.LABEL_KEY_KEY));
        apiExecutor.expectStatus(putTestRunLabelsMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(putTestRunLabelsMethod);
        String testRunLabels = new TestRunServiceAPIImplV1().getTestRunLabels(testRunId);
        Assert.assertTrue(testRunLabels.contains(R.TESTDATA.get(ConfigConstant.LABEL_VALUE_KEY)),
                "Label with value: " + R.TESTDATA.get(ConfigConstant.LABEL_VALUE_KEY) + " was not found!");
        Assert.assertTrue(testRunLabels.contains(R.TESTDATA.get(ConfigConstant.LABEL_KEY_KEY)),
                "Label with key: " + R.TESTDATA.get(ConfigConstant.LABEL_KEY_KEY) + " was not found!");
    }
}
