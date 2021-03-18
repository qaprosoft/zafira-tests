package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.integrationInfo.GetIntegrationInfoMethod;
import com.qaprosoft.zafira.api.integrationInfo.GetPublicIntegrationInfoMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.IntegrationInfoService;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

import java.util.List;

public class IntegrationInfoServiceImpl implements IntegrationInfoService {
    private static final Logger LOGGER = Logger.getLogger(JobServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();


    @Override
    public String getAllIntegrationsInfo() {
        GetIntegrationInfoMethod getIntegrationInfoMethod = new GetIntegrationInfoMethod();
        apiExecutor.expectStatus(getIntegrationInfoMethod, HTTPStatusCodeType.OK);
        return apiExecutor.callApiMethod(getIntegrationInfoMethod);
    }

    @Override
    public String getURLByIntegrationName(String name) {
        GetPublicIntegrationInfoMethod getPublicIntegrationInfoMethod = new GetPublicIntegrationInfoMethod();
        apiExecutor.expectStatus(getPublicIntegrationInfoMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getPublicIntegrationInfoMethod);
        LOGGER.info("Required integration name: "+name);
        List<String> integrationNames = JsonPath.from(rs).getList(JSONConstant.NAME);
        LOGGER.info("List of integration names: "+integrationNames);
        int indexOfName = integrationNames.indexOf(name);
        LOGGER.info("Index of name: "+indexOfName);
        String url = JsonPath.from(rs).getString("[" + indexOfName + "].url");
        return url;
    }
}
