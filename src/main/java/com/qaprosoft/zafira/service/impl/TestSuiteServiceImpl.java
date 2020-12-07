package com.qaprosoft.zafira.service.impl;

import io.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.testSuiteController.PostTestSuiteMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.service.TestSuiteService;
import org.apache.log4j.Logger;

public class TestSuiteServiceImpl implements TestSuiteService {
    private static final Logger LOGGER = Logger.getLogger(TestSuiteServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int create() {
        String response = apiExecutor.callApiMethod(new PostTestSuiteMethod());
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }
}
