package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.IntegrationInfoMethods.GetIntegrationInfoMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.IntegrationInfoService;
import org.apache.log4j.Logger;

public class IntegrationInfoServiceImpl implements IntegrationInfoService {
    private static final Logger LOGGER = Logger.getLogger(JobServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();


    @Override
    public String getAllIntegretionsInfo() {
        GetIntegrationInfoMethod getIntegrationInfoMethod = new GetIntegrationInfoMethod();
        apiExecutor.expectStatus(getIntegrationInfoMethod, HTTPStatusCodeType.OK);
        return apiExecutor.callApiMethod(getIntegrationInfoMethod);
    }
}
