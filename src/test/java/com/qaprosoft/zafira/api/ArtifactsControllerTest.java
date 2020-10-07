package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.artifactsController.GetLogsV1Method;
import com.qaprosoft.zafira.api.artifactsController.GetScreenshotsV1Method;
import com.qaprosoft.zafira.api.artifactsController.PostLogsV1Method;
import com.qaprosoft.zafira.api.artifactsController.PostScreenshotsV1Method;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ArtifactsControllerV1Impl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceAPIV1Impl;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

import java.io.IOException;


public class ArtifactsControllerTest extends ZafiraAPIBaseTest {

    private final static Logger LOGGER = Logger.getLogger(ArtifactsControllerTest.class);

    @Test
    public void testGetLogs() {
        int testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        new ArtifactsControllerV1Impl().createLogRecord(testRunId, testId);
        GetLogsV1Method getLogsV1Method = new GetLogsV1Method(testRunId, testId);
        apiExecutor.expectStatus(getLogsV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getLogsV1Method);
        apiExecutor.validateResponse(getLogsV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
    }

    @Test
    public void testGetScreenshots() throws IOException {
        int testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        String filePath = R.TESTDATA.get(ConfigConstant.SCREENSHOT_PATH_KEY);
        new ArtifactsControllerV1Impl().createScreenshots(testRunId, testId, filePath);
        GetScreenshotsV1Method getScreenshotsV1Method = new GetScreenshotsV1Method(testRunId, testId);
        apiExecutor.expectStatus(getScreenshotsV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getScreenshotsV1Method);
        apiExecutor.validateResponse(getScreenshotsV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
    }

    @Test
    public void testSendingTestExecutionLogs() {
        int testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        PostLogsV1Method postLogsV1Method = new PostLogsV1Method(testRunId, testId);
        apiExecutor.expectStatus(postLogsV1Method, HTTPStatusCodeType.ACCEPTED);
        apiExecutor.callApiMethod(postLogsV1Method);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
    }

    @Test
    public void testSendingTestScreenshots() throws IOException {
        int testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        String filePath = R.TESTDATA.get(ConfigConstant.SCREENSHOT_PATH_KEY);
        PostScreenshotsV1Method postScreenshotsV1Method = new PostScreenshotsV1Method(testRunId, testId, filePath);
        apiExecutor.expectStatus(postScreenshotsV1Method, HTTPStatusCodeType.CREATED);
        apiExecutor.callApiMethod(postScreenshotsV1Method);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
    }

}
