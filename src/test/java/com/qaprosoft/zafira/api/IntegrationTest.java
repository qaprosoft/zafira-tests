package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.IntegrationInfoServiceImpl;
import com.qaprosoft.zafira.service.impl.IntegrationServiceImpl;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class IntegrationTest extends ZariraAPIBaseTest {
    private static final Logger LOGGER = Logger.getLogger(IntegrationTest.class);

    @Test
    public void testGetAllIntegrations() {
        GetAllIntegrationsMethod getAllIntegrationsMethod = new GetAllIntegrationsMethod();
        apiExecutor.expectStatus(getAllIntegrationsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllIntegrationsMethod);
        apiExecutor.validateResponse(getAllIntegrationsMethod,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @DataProvider(name = "updateIntegration")
    public Object[][] getRerunFailuresFlag() {
        return new Object[][]{{false}, {true}};
    }

    @Test(dataProvider = "updateIntegration")
    public void testUpdatesJiraIntegrationById(boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.JIRA_INTEGRATION_ID_KEY);
        String IntegrationResponse = new IntegrationServiceImpl().getUpdateIntegrationResponse(
                "api/integration/_put/rq_for_JIRA.json", integrationId, enabledType);
        Assert.assertEquals(enabledType, JsonPath.from(IntegrationResponse)
                .getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY), "Enabled was not update!");
    }

    @Test(dataProvider = "updateIntegration")
    public void testUPdateTestrailIntegrationById(boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.TESTRAIL_INTEGRATION_ID_KEY);
        String IntegrationResponse = new IntegrationServiceImpl().getUpdateIntegrationResponse(
                "api/integration/_put/rq_for_TESTRAIL.json", integrationId, enabledType);
        Assert.assertEquals(enabledType, JsonPath.from(IntegrationResponse)
                .getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY), "Enabled was not update!");


    }


}

