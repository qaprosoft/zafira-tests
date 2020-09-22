package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.PostTestSuiteMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.TestSuiteService;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;

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
