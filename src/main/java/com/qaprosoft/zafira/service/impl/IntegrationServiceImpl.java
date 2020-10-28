package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.GetAllIntegrationGroupsMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.service.IntegrationService;
import org.apache.log4j.Logger;

public class IntegrationServiceImpl implements IntegrationService {
    private static final Logger LOGGER = Logger.getLogger(JobServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public String getIntegrationGroupName() {
        String rs = apiExecutor.callApiMethod(new GetAllIntegrationGroupsMethod());
        return JsonPath.from(rs).getString(JSONConstant.INTEGRATION_GROUP_NAME);
    }

    @Override
    public int getIntegrationGroupId() {
        String rs = apiExecutor.callApiMethod(new GetAllIntegrationGroupsMethod());
        return JsonPath.from(rs).getInt(JSONConstant.INTEGRATION_GROUP_ID);
    }
}
