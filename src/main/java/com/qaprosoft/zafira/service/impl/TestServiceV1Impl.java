package com.qaprosoft.zafira.service.impl;


import com.qaprosoft.zafira.api.testRunController.v1.DeleteTestByIdV1Method;
import com.qaprosoft.zafira.api.testRunController.v1.PostStartTestsInTestRunV1HeadlessMethod;
import com.qaprosoft.zafira.api.testRunController.v1.PostStartTestsInTestRunV1Method;
import com.qaprosoft.zafira.api.testRunController.v1.PutFinishTestInTestRunV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.TestServiceAPIV1;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TestServiceV1Impl implements TestServiceAPIV1 {
    private static final Logger LOGGER = Logger.getLogger(TestServiceV1Impl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int startTest(int testRunId) {
        PostStartTestsInTestRunV1Method postStartTestsInTestRunV1Method = new PostStartTestsInTestRunV1Method(testRunId);
        apiExecutor.expectStatus(postStartTestsInTestRunV1Method, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postStartTestsInTestRunV1Method);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public List<Integer> startTests(int testRunId, int numOfTests) {
        List<Integer> testIdsList = new ArrayList<>() ;
        for (int i = 0; i < numOfTests; ++i) {
            PostStartTestsInTestRunV1Method postStartTestsInTestRunV1Method = new PostStartTestsInTestRunV1Method(testRunId);
            apiExecutor.expectStatus(postStartTestsInTestRunV1Method, HTTPStatusCodeType.OK);
            String response = apiExecutor.callApiMethod(postStartTestsInTestRunV1Method);
            int id = JsonPath.from(response).getInt(JSONConstant.ID_KEY);
            testIdsList.add(id);
        }
        return testIdsList;
    }

    @Override
    public String finishTestAsResult(int testRunId, int testId, String result) {
        PutFinishTestInTestRunV1Method putFinishTestInTestRunV1Method = new PutFinishTestInTestRunV1Method(testRunId, testId, result);
        apiExecutor.expectStatus(putFinishTestInTestRunV1Method, HTTPStatusCodeType.OK);
        String response =  apiExecutor.callApiMethod(putFinishTestInTestRunV1Method);
        return JsonPath.from(response).getString(JSONConstant.RESULT);
    }

    @Override
    public int createTestHeadless(int testRunId) {
        PostStartTestsInTestRunV1HeadlessMethod postStartTestsInTestRunV1Method
                = new PostStartTestsInTestRunV1HeadlessMethod(testRunId);
        apiExecutor.expectStatus(postStartTestsInTestRunV1Method, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postStartTestsInTestRunV1Method);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void deleteTest(int testRunId, int id) {
        DeleteTestByIdV1Method postStartTestsInTestRunV1Method = new DeleteTestByIdV1Method(testRunId,id);
        apiExecutor.callApiMethod(postStartTestsInTestRunV1Method);
    }
}
