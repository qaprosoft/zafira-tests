package com.qaprosoft.zafira.service.impl;

import io.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.testRunController.*;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.TestRunServiceAPI;
import org.apache.log4j.Logger;

import java.util.List;

public class TestRunServiceAPIImpl implements TestRunServiceAPI {
    private static final Logger LOGGER = Logger.getLogger(TestRunServiceAPIImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int create(int testSuiteId, int jobId) {
        String response = apiExecutor.callApiMethod(new PostStartTestRunMethod(testSuiteId, jobId));
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public String getCiRunId(int testSuiteId) {
        return JsonPath.from(
                apiExecutor.callApiMethod(new GetTestRunByIdMethod(testSuiteId)))
                .getString(JSONConstant.CI_RUN_ID_KEY);
    }

    @Override
    public String finishTestRun(int testRunId) {
        String response = apiExecutor.callApiMethod(new PostFinishTestRunMethod(testRunId));
        return JsonPath.from(response).getString(JSONConstant.STATUS_KEY);
    }

    @Override
    public List<Integer> getAll(String searchCriteriaType, int searchCriteriaId) {
        String response = apiExecutor.callApiMethod(new GetTestRunBySearchCriteriaMethod(searchCriteriaType, searchCriteriaId));
        return JsonPath.from(response).getList(JSONConstant.ALL_TEST_RUN_ID_BY_SEARCH_CRITERIA_KEY);
    }

    @Override
    public void deleteById(int testRunId) {
        apiExecutor.callApiMethod(new DeleteTestRunMethod(testRunId));
    }

    @Override
    public String getTestRunStatus(int testSuiteId) {
        return JsonPath.from(
                apiExecutor.callApiMethod(new GetTestRunByIdMethod(testSuiteId)))
                .getString(JSONConstant.STATUS_KEY);
    }

    @Override
    public String createTestRunAndReturnCiRunId(int testSuiteId, int jobId) {
        String response = apiExecutor.callApiMethod(new PostStartTestRunMethod(testSuiteId, jobId));
        return JsonPath.from(response).getString(JSONConstant.CI_RUN_ID_KEY);
    }

    @Override
    public void createAIAnalysis(int testRunId) {
        PostAIAnalysisMethod postAIAnalysisMethod = new PostAIAnalysisMethod(testRunId);
        apiExecutor.expectStatus(postAIAnalysisMethod, HTTPStatusCodeType.ACCEPTED);
        apiExecutor.callApiMethod(postAIAnalysisMethod);
    }
}
