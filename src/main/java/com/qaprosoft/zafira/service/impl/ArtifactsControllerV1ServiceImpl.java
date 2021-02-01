package com.qaprosoft.zafira.service.impl;

import io.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.artifactsController.PostLogsV1Method;
import com.qaprosoft.zafira.api.artifactsController.PostScreenshotsV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.ArtifactsControllerV1Service;
import org.apache.log4j.Logger;

public class ArtifactsControllerV1ServiceImpl implements ArtifactsControllerV1Service {
    private static final Logger LOGGER = Logger.getLogger(ArtifactsControllerV1ServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public String createLogRecord(int testRunId, int testId) {
        PostLogsV1Method postLogsV1Method = new PostLogsV1Method(testRunId, testId);
        apiExecutor.expectStatus(postLogsV1Method, HTTPStatusCodeType.ACCEPTED);
        return apiExecutor.callApiMethod(postLogsV1Method);
    }

    @Override
    public String createScreenshot(int testRunId, int testId, String filePath) {
        PostScreenshotsV1Method postScreenshotsV1Method = new PostScreenshotsV1Method(testRunId, testId, filePath);
        apiExecutor.expectStatus(postScreenshotsV1Method, HTTPStatusCodeType.CREATED);
        return apiExecutor.callApiMethod(postScreenshotsV1Method);
    }

    @Override
    public String createScreenshotWithHeader(int testRunId, int testId, String filePath, int milliseconds) {
        PostScreenshotsV1Method postScreenshotsV1Method = new PostScreenshotsV1Method(testRunId, testId, filePath);
        postScreenshotsV1Method.setHeaders("x-zbr-screenshot-captured-at=" + milliseconds);
        apiExecutor.expectStatus(postScreenshotsV1Method, HTTPStatusCodeType.CREATED);
        return apiExecutor.callApiMethod(postScreenshotsV1Method);
    }

    @Override
    public int getTotalResults(int testRunId, int testId, String filePath) {
        return JsonPath.from
                (apiExecutor.callApiMethod(new PostScreenshotsV1Method(testRunId, testId, filePath))).
                getInt(JSONConstant.TOTAL_RESULTS_KEY);
    }
}
