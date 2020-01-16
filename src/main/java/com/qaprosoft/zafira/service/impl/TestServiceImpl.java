package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.GetTestByTestRunIdMethod;
import com.qaprosoft.zafira.api.PostFinishTestMethod;
import com.qaprosoft.zafira.api.PostStartTestMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.TestService;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.List;

public class TestServiceImpl implements TestService {
    private static final Logger LOGGER = Logger.getLogger(TestServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int getId(String accessToken, int testCaseId, int testRunId) {
        String response = apiExecutor.callApiMethod(new PostStartTestMethod(accessToken, testCaseId, testRunId),
                HTTPStatusCodeType.OK, true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void finishTest(String accessToken, int testCaseId, int testRunId, int testId) {
        apiExecutor.callApiMethod(new PostFinishTestMethod(accessToken, testCaseId, testRunId, testId),
                HTTPStatusCodeType.OK, true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Override
    public List<Integer> getAllArtifacts(String accessToken, int testRunId) {
        String response = apiExecutor.callApiMethod(new GetTestByTestRunIdMethod(accessToken, testRunId),
                HTTPStatusCodeType.OK, true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        return JsonPath.from(response).getList(JSONConstant.ARTIFACT_ID_KEY);
    }
}
