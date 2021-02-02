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
        apiExecutor.validateResponse(postTestArtifact,JSONCompareMode.STRICT);
    }

    @Test
    public void testSendTestRunArtifact() {
        File uploadFile = new File(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY));
        PostTestRunArtifactMethod postTestRunArtifact = new PostTestRunArtifactMethod(testRunId+1, uploadFile);
        apiExecutor.expectStatus(postTestRunArtifact, HTTPStatusCodeType.CREATED);
        apiExecutor.callApiMethod(postTestRunArtifact);
        apiExecutor.validateResponse(postTestRunArtifact,JSONCompareMode.STRICT);
    }

    @Test
    public void testSendTestArtifactReferences() {
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        PutTestArtifactReferencesMethod putTestArtifactReferencesMethod = new PutTestArtifactReferencesMethod(testRunId, testId);
        apiExecutor.expectStatus(putTestArtifactReferencesMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(putTestArtifactReferencesMethod);
    }

    @Test
    public void testSendTestRunArtifactReferences() {
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        PutTestRunArtifactReferencesMethod putTestRunArtifactReferencesMethod = new PutTestRunArtifactReferencesMethod(testRunId, testId);
        apiExecutor.expectStatus(putTestRunArtifactReferencesMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(putTestRunArtifactReferencesMethod);
    }

}
