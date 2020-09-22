package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class IntegrationGroupsTest extends ZafiraAPIBaseTest {

    @Test
    public void testGetAllIntegrationGroups(){
        GetAllIntegrationGroupsMethod getAllIntegrationGroupsMethod = new GetAllIntegrationGroupsMethod();
        apiExecutor.expectStatus(getAllIntegrationGroupsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllIntegrationGroupsMethod);
        apiExecutor.validateResponse(getAllIntegrationGroupsMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
