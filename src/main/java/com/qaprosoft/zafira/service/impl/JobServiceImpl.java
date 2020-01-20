package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.PostJobMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.JobService;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class JobServiceImpl implements JobService {
    private static final Logger LOGGER = Logger.getLogger(JobServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int create() {
        String response = apiExecutor.callApiMethod(new PostJobMethod());
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

}
