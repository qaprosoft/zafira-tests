package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.GetIntegrationInfoByIdMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.enums.IntegrationGroupType;
import com.qaprosoft.zafira.service.IntegrationService;
import org.apache.log4j.Logger;

public class IntegrationServiceImpl implements IntegrationService {
    private static final Logger LOGGER = Logger.getLogger(JobServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public boolean isIntegrationEnabled(int id, IntegrationGroupType integrationGroup) {
        String rs = apiExecutor.callApiMethod(new GetIntegrationInfoByIdMethod(id, integrationGroup),
                HTTPStatusCodeType.OK, false, null);
        return JsonPath.from(rs).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY);
    }
}
