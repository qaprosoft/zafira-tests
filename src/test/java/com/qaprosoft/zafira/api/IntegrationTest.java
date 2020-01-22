package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.IntegrationMethods.GetAllIntegrationsMethod;
import com.qaprosoft.zafira.api.IntegrationMethods.PutIntegrationByIdMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.enums.IntegrationRqPathType;
import com.qaprosoft.zafira.service.impl.IntegrationInfoServiceImpl;
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
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(IntegrationRqPathType.JIRA.getPath(),
                integrationId, enabledType);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY),
                "Enabled was not update!");
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not connect!");
    }

    @Test(dataProvider = "updateIntegration")
    public void testUpdateTestrailIntegrationById(boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.TESTRAIL_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(IntegrationRqPathType.TESTRAIL.getPath(),
                integrationId, enabledType);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY),
                "Enabled was not update!");
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not connect!");
    }

    @Test(dataProvider = "updateIntegration")
    public void testUpdateQTestIntegrationById(boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.QTEST_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(IntegrationRqPathType.QTEST.getPath(),
                integrationId, enabledType);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY),
                "Enabled was not update!");
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not connect!");
    }

    @Test(dataProvider = "updateIntegration")
    public void testUpdateZebrunnerIntegrationById(boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.ZEBRUNNER_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(IntegrationRqPathType.ZEBRUNNER.getPath(),
                integrationId, enabledType);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY),
                "Enabled was not update!");
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not connect!");
    }

    @Test(dataProvider = "updateIntegration")
    public void testUpdateSeleniumIntegrationById(boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.SELENIUM_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(IntegrationRqPathType.SELENIUM.getPath(),
                integrationId, enabledType);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY),
                "Enabled was not update!");
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not connect!");
    }

    @Test(dataProvider = "updateIntegration")
    public void testUpdateBrowserstackIntegrationById(boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.BROWSERSTACK_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(IntegrationRqPathType.BROWSERSTACK.getPath(),
                integrationId, enabledType);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY),
                "Enabled was not update!");
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not connect!");
    }

    @Test(dataProvider = "updateIntegration")
    public void testUpdateMcloudIntegrationById(boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.MCLOUD_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(IntegrationRqPathType.MCLOUD.getPath(),
                integrationId, enabledType);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY),
                "Enabled was not update!");
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not connect!");
    }

    @Test(dataProvider = "updateIntegration")
    public void testUpdateSaucelabsIntegrationById(boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.SAUCELABS_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(IntegrationRqPathType.SAUCELABS.getPath(), integrationId, enabledType);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY),
                "Enabled was not update!");
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not connect!");
    }

    @Test(dataProvider = "updateIntegration")
    public void testUpdateLambdatestIntegrationById(boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.LAMBDATEST_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(IntegrationRqPathType.LAMBDATEST.getPath(),
                integrationId, enabledType);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY),
                "Enabled was not update!");
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not connect!");
    }

    @Test(dataProvider = "updateIntegration")
    public void testUpdateAmazonIntegrationById(boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.AMAZON_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(IntegrationRqPathType.AMAZON.getPath(),
                integrationId, enabledType);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY),
                "Enabled was not update!");
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not connect!");
    }

    @Test(dataProvider = "updateIntegration")
    public void testUpdatesSlackIntegrationById(boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.SLACK_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(IntegrationRqPathType.SLACK.getPath(),
                integrationId, enabledType);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY),
                "Enabled was not update!");
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not connect!");
    }

    @Test(dataProvider = "updateIntegration")
    public void testUpdateRabbitmqIntegrationById(boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.RABBITMQ_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(IntegrationRqPathType.RABBITMQ.getPath(),
                integrationId, enabledType);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY),
                "Enabled was not update!");
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not connect!");
    }

    @Test(dataProvider = "updateIntegration")
    public void testUpdateEmailIntegrationById(boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.EMAIL_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(IntegrationRqPathType.EMAIL.getPath(),
                integrationId, enabledType);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY),
                "Enabled was not update!");
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not connect!");
    }
}

