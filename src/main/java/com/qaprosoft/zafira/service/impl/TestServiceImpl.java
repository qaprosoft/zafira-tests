package com.qaprosoft.zafira.service.impl;

import io.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.testController.*;
import com.qaprosoft.zafira.api.testRunController.GetTestByTestRunIdMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.TestService;
import org.apache.log4j.Logger;

import java.util.List;

public class TestServiceImpl implements TestService {
    private static final Logger LOGGER = Logger.getLogger(TestServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int create(int testCaseId, int testRunId) {
        String response = apiExecutor.callApiMethod(new PostStartTestMethod(testCaseId, testRunId));
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void finishTest(int testCaseId, int testRunId, int testId) {
        apiExecutor.callApiMethod(new PostFinishTestMethod(testCaseId, testRunId, testId));
    }

    @Override
    public String getAllTest(int testRunId) {
        PostRetrieveTestBySearchCriteriaMethod postRetrieveTestBySearchCriteriaMethod = new PostRetrieveTestBySearchCriteriaMethod(testRunId);
        apiExecutor.expectStatus(postRetrieveTestBySearchCriteriaMethod, HTTPStatusCodeType.OK);
        return apiExecutor.callApiMethod(postRetrieveTestBySearchCriteriaMethod);
    }

    @Override
    public void updateTestStatus(int testId, int testSuiteId, int
            jobId, String expectedTestStatusValue) {
        PutUpdateTestStatusMethod putUpdateTestStatusMethod = new PutUpdateTestStatusMethod(testId, testSuiteId,
                jobId, expectedTestStatusValue);
        apiExecutor.expectStatus(putUpdateTestStatusMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putUpdateTestStatusMethod);
    }

    @Override
    public void updateTestStacktraceLabel(int testId, String stacktraceLabelsName) {
        PutStacktraceLabelsMethod putStacktraceLabelsMethod = new PutStacktraceLabelsMethod(testId, stacktraceLabelsName);
        apiExecutor.expectStatus(putStacktraceLabelsMethod, HTTPStatusCodeType.OK);
    }
}
