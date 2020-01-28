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
    public Object[][] getEnabledType() {
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

    @DataProvider(name = "updateZebrunnerIntegrationNegative")
    public Object[][] getZebrunnerRqPath() {
        return new Object[][]{{IntegrationRqPathType.ZEBRUNNER_NEGATIVE.getPath(), false}, {IntegrationRqPathType.ZEBRUNNER.getPath(), true}};
    }

    @Test(description = "invalid_URL", dataProvider = "updateZebrunnerIntegrationNegative")
    public void testUpdateZebrunnerIntegrationByIdNegative(String rqPath, boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.ZEBRUNNER_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(rqPath, integrationId, true);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not work correctly!");
    }

    @DataProvider(name = "updateSeleniumIntegrationNegative")
    public Object[][] getSeleniumRqPath() {
        return new Object[][]{{IntegrationRqPathType.SELENIUM_NEGATIVE.getPath(), false}, {IntegrationRqPathType.SELENIUM.getPath(), true}};
    }

    @Test(description = "invalid_URL", dataProvider = "updateSeleniumIntegrationNegative")
    public void testUpdateSeleniumIntegrationByIdNegative(String rqPath, boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.SELENIUM_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(rqPath, integrationId, true);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not work correctly!");
    }

    @DataProvider(name = "updateBrowserstackIntegrationNegative")
    public Object[][] getBrowserstackRqPath() {
        return new Object[][]{{IntegrationRqPathType.BROWSERSTACK_NEGATIVE.getPath(), false}, {IntegrationRqPathType.BROWSERSTACK.getPath(), true}};
    }

    @Test(description = "invalid_password", dataProvider = "updateBrowserstackIntegrationNegative")
    public void testUpdateBrowserstackIntegrationByIdNegative(String rqPath, boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.BROWSERSTACK_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(rqPath, integrationId, true);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not work correctly!");
    }

    @DataProvider(name = "updateMcloudIntegrationNegative")
    public Object[][] getMcloudRqPath() {
        return new Object[][]{{IntegrationRqPathType.MCLOUD_NEGATIVE.getPath(), false}, {IntegrationRqPathType.MCLOUD.getPath(), true}};
    }

    @Test(description = "invalid_URL", dataProvider = "updateMcloudIntegrationNegative")
    public void testUpdateMcloudIntegrationByIdNegative(String rqPath, boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.MCLOUD_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(rqPath, integrationId, true);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not work correctly!");
    }

    @DataProvider(name = "updateSaucelabsIntegrationNegative")
    public Object[][] getSaucelabsRqPath() {
        return new Object[][]{{IntegrationRqPathType.SAUCELABS_NEGATIVE.getPath(), false}, {IntegrationRqPathType.SAUCELABS.getPath(), true}};
    }

    @Test(description = "invalid_password", dataProvider = "updateSaucelabsIntegrationNegative")
    public void testUpdateSaucelabsIntegrationByIdNegative(String rqPath, boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.SAUCELABS_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(rqPath, integrationId, true);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not work correctly!");
    }

    @DataProvider(name = "updateLambdatestIntegrationNegative")
    public Object[][] getLambdatestRqPath() {
        return new Object[][]{{IntegrationRqPathType.LAMBDATEST_NEGATIVE.getPath(), false}, {IntegrationRqPathType.LAMBDATEST.getPath(), true}};
    }

    @Test(description = "invalid_password", dataProvider = "updateLambdatestIntegrationNegative")
    public void testUpdateLambdatestIntegrationByIdNegative(String rqPath, boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.LAMBDATEST_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(rqPath, integrationId, true);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not work correctly!");
    }

    @DataProvider(name = "updateAmazonIntegrationNegative")
    public Object[][] getAmazonRqPath() {
        return new Object[][]{{IntegrationRqPathType.AMAZON_NEGATIVE.getPath(), false}, {IntegrationRqPathType.AMAZON.getPath(), true}};
    }

    @Test(description = "invalid_access_key", dataProvider = "updateAmazonIntegrationNegative")
    public void testUpdateAmazonIntegrationByIdNegative(String rqPath, boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.AMAZON_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(rqPath, integrationId, true);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not work correctly!");
    }

    @DataProvider(name = "updateRabbitmqIntegrationNegative")
    public Object[][] getRabbitmqRqPath() {
        return new Object[][]{{IntegrationRqPathType.RABBITMQ_NEGATIVE.getPath(), false}, {IntegrationRqPathType.RABBITMQ.getPath(), true}};
    }

    @Test(description = "invalid_port", dataProvider = "updateRabbitmqIntegrationNegative")
    public void testUpdateRabbitmqIntegrationByIdNegative(String rqPath, boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.RABBITMQ_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(rqPath, integrationId, true);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not work correctly!");
    }

    @DataProvider(name = "updateEmailIntegrationNegative")
    public Object[][] getEmailRqPath() {
        return new Object[][]{{IntegrationRqPathType.EMAIL_NEGATIVE.getPath(), false}, {IntegrationRqPathType.EMAIL.getPath(), true}};
    }

    @Test(description = "invalid_port", dataProvider = "updateEmailIntegrationNegative")
    public void testUpdateEmailIntegrationByIdNegative(String rqPath, boolean enabledType) {
        String response = new IntegrationInfoServiceImpl().getAllIntegretionsInfo();
        int integrationId = JsonPath.from(response).get(JSONConstant.EMAIL_INTEGRATION_ID_KEY);
        PutIntegrationByIdMethod putIntegrationByIdMethod = new PutIntegrationByIdMethod(rqPath, integrationId, true);
        apiExecutor.expectStatus(putIntegrationByIdMethod, HTTPStatusCodeType.OK);
        String integrationResponse = apiExecutor.callApiMethod(putIntegrationByIdMethod);
        apiExecutor.validateResponse(putIntegrationByIdMethod, JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(enabledType, JsonPath.from(integrationResponse).getBoolean(JSONConstant.INTEGRATION_CONNECTED_KEY),
                "Integration is not work correctly!");
    }
}

