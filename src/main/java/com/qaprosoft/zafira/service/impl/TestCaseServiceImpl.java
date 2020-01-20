package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.PostTestCaseMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.TestCaseService;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;

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
