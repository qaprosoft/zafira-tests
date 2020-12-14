package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.tagController.GetIntegrationInformationByTagMethod;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import io.restassured.path.json.JsonPath;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TcmDataExportControllerTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER =  LoggerFactory.getLogger(TestControllerTest.class);
    private final int TESTS_TO_ADD = 1;

    @DataProvider(name = "data-provider-tools")
    public Object[][] dataProviderMethod() {
        return new Object[][]{{ConfigConstant.QTEST_KEY}, {ConfigConstant.TESTRAIL_KEY}};
    }

    @Test(dataProvider = "data-provider-tools")
    public void testGetIntegrationInformationByTag(String data) {
        int testRunId = createTestRun(TESTS_TO_ADD);
        String ciRunId = new TestRunServiceAPIImplV1().getCiRunId(testRunId);
        GetIntegrationInformationByTagMethod getIntegrationInformationByTagMethod = new GetIntegrationInformationByTagMethod(ciRunId, data);
        apiExecutor.expectStatus(getIntegrationInformationByTagMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getIntegrationInformationByTagMethod);
        apiExecutor.validateResponse(getIntegrationInformationByTagMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        LOGGER.info("TestInfo:" + JsonPath.from(rs).getList(JSONConstant.TEST_INFO_KEY));
        Assert.assertNotEquals(JsonPath.from(rs).getList(JSONConstant.TEST_INFO_KEY).size(), 0, "TestInfo is not present!");
        LOGGER.info("CustomParams:" + JsonPath.from(rs).getJsonObject(JSONConstant.CUSTOM_PARAMS_KEY));
        Assert.assertTrue(JsonPath.from(rs).getJsonObject(JSONConstant.CUSTOM_PARAMS_KEY).toString().contains(data), "CustomParams is not present!");
    }
}
