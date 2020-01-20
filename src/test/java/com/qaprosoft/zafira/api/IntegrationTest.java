package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class IntegrationTest extends ZariraAPIBaseTest {

    @Test
    public void testGetIntegrationConnectionInfo() {
        GetIntegrationInfoMethod getIntegrationInfoMethod = new GetIntegrationInfoMethod();
        apiExecutor.expectStatus(getIntegrationInfoMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getIntegrationInfoMethod);
        apiExecutor.validateResponse(getIntegrationInfoMethod,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
