package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.testRunController.*;
import com.qaprosoft.zafira.api.testRunController.v1.PostStartTestRunV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.service.TestRunServiceAPIV1;
import org.apache.log4j.Logger;

import java.util.List;

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
    public void deleteById( int testRunId) {
        apiExecutor.callApiMethod(new DeleteTestRunMethod(testRunId));
    }
}
