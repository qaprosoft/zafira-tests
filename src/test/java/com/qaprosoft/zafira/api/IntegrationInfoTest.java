package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.integrationInfo.GetIntegrationInfoByIdMethod;
import com.qaprosoft.zafira.api.integrationInfo.GetIntegrationInfoMethod;
import com.qaprosoft.zafira.api.integrationInfo.GetPublicIntegrationInfoMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.IntegrationServiceImpl;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class IntegrationInfoTest extends ZafiraAPIBaseTest {

    @Test
    public void testGetIntegrationConnectionInfo() {
        GetIntegrationInfoMethod getIntegrationInfoMethod = new GetIntegrationInfoMethod();
        apiExecutor.expectStatus(getIntegrationInfoMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getIntegrationInfoMethod);
        apiExecutor.validateResponse(getIntegrationInfoMethod,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetPublicIntegrationInfo() {
        GetPublicIntegrationInfoMethod getPublicIntegrationInfoMethod = new GetPublicIntegrationInfoMethod();
        apiExecutor.expectStatus(getPublicIntegrationInfoMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getPublicIntegrationInfoMethod);
        apiExecutor.validateResponse(getPublicIntegrationInfoMethod,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetIntegrationConnectionInfoById() {
        IntegrationServiceImpl integrationService = new IntegrationServiceImpl();
        int id = integrationService.getIntegrationGroupId();
        String integrationGroupName = integrationService.getIntegrationGroupName();
        GetIntegrationInfoByIdMethod getIntegrationInfoByIdMethod = new GetIntegrationInfoByIdMethod(id, integrationGroupName);
        apiExecutor.expectStatus(getIntegrationInfoByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getIntegrationInfoByIdMethod);
        apiExecutor.validateResponse(getIntegrationInfoByIdMethod,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

}
