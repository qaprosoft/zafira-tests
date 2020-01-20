package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.GetTestRunBySearchCriteriaMethod;
import com.qaprosoft.zafira.api.GetTestRunMethod;
import com.qaprosoft.zafira.api.PostFinishTestRunMethod;
import com.qaprosoft.zafira.api.PostStartTestRunMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.TestRunServiceAPI;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.List;

public class TestRunServiceAPIImpl implements TestRunServiceAPI {
    private static final Logger LOGGER = Logger.getLogger(TestRunServiceAPIImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int create(String accessToken, int testSuiteId, int jobId) {
        String response = apiExecutor.callApiMethod(new PostStartTestRunMethod(accessToken, testSuiteId, jobId),
                HTTPStatusCodeType.OK, true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public String getCiRunId(String accessToken, int testSuiteId) {
        return JsonPath.from(
                apiExecutor.callApiMethod(new GetTestRunMethod(accessToken, testSuiteId), HTTPStatusCodeType.OK, false, null))
                .getString(JSONConstant.CI_RUN_ID_KEY);
    }

    @Override
    public String finishTestRun(String accessToken, int testRunId) {
        String response = apiExecutor.callApiMethod(new PostFinishTestRunMethod(accessToken, testRunId),
                HTTPStatusCodeType.OK, true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        return JsonPath.from(response).getString(JSONConstant.STATUS_KEY);
    }

    @Override
    public List<Integer> getAll(String accessToken, String searchCriteriaType, int searchCriteriaId) {
        String response = apiExecutor.callApiMethod(new GetTestRunBySearchCriteriaMethod(accessToken, searchCriteriaType, searchCriteriaId),
                HTTPStatusCodeType.OK, true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        return JsonPath.from(response).getList(JSONConstant.ALL_TEST_ID_BY_TEST_SUITE_ID_KEY);
    }

    @Override
    public void deleteById(String accessToken, int testRunId) {
        apiExecutor.callApiMethod(new GetTestRunMethod(accessToken, testRunId), HTTPStatusCodeType.OK, false, null);
    }
}
