package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.testRunController.*;
import com.qaprosoft.zafira.api.testRunController.v1.GetTestsByCiRunIdV1Method;
import com.qaprosoft.zafira.api.testRunController.v1.PostStartTestRunV1Method;
import com.qaprosoft.zafira.api.testRunController.v1.PostStartTestsInTestRunV1Method;
import com.qaprosoft.zafira.api.testRunController.v1.PutFinishTestRunV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.TestRunServiceAPIV1;
import org.apache.log4j.Logger;


public class TestRunServiceAPIImplV1 implements TestRunServiceAPIV1 {
    private static final Logger LOGGER = Logger.getLogger(TestRunServiceAPIImplV1.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int create() {
        String response = apiExecutor.callApiMethod(new PostStartTestRunV1Method());
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public String getCiRunId(int testRunId) {
        return JsonPath.from(
                apiExecutor.callApiMethod(new GetTestRunMethod(testRunId)))
                .getString(JSONConstant.CI_RUN_ID_KEY);
    }

    @Override
    public void finishTestRun(int testRunId) {
        PutFinishTestRunV1Method putFinishTestRunV1Method = new PutFinishTestRunV1Method(testRunId);
        apiExecutor.expectStatus(putFinishTestRunV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putFinishTestRunV1Method);
    }
}