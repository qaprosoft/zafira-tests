package com.qaprosoft.zafira.service.impl;

import io.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.testCase.PostTestCaseMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.service.TestCaseService;
import org.apache.log4j.Logger;

public class TestCaseServiceImpl implements TestCaseService {
    private static final Logger LOGGER = Logger.getLogger(TestCaseServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int create(int testSuiteId) {
        String response = apiExecutor.callApiMethod(new PostTestCaseMethod(testSuiteId));
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }
}
