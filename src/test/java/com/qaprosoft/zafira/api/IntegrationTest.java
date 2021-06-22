package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.integration.GetAllIntegrationsMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class IntegrationTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = Logger.getLogger(IntegrationTest.class);

    @Test
    public void testGetAllIntegrations() {

        GetAllIntegrationsMethod getAllIntegrationsMethod = new GetAllIntegrationsMethod();
        apiExecutor.expectStatus(getAllIntegrationsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllIntegrationsMethod);
        apiExecutor.validateResponse(getAllIntegrationsMethod,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
