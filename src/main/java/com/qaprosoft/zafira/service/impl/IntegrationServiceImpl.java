package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.GetIntegrationInfoByIdMethod;
import com.qaprosoft.zafira.api.PutIntegrationByIdMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.enums.IntegrationGroupType;
import com.qaprosoft.zafira.service.IntegrationService;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class IntegrationServiceImpl implements IntegrationService {
    private static final Logger LOGGER = Logger.getLogger(JobServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public boolean isIntegrationEnabled(int id, IntegrationGroupType integrationGroup) {
        String rs = apiExecutor.callApiMethod(new GetIntegrationInfoByIdMethod(id, integrationGroup));
        return JsonPath.from(rs).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY);
    }

    @Override
    public String getUpdateIntegrationResponse(String rqPath, int integrationId, Boolean enabledType) {
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(rqPath, integrationId,
                enabledType);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        return response;
    }
}
