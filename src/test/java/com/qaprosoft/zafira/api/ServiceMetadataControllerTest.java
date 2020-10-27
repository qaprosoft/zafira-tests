package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.serviceMetadataController.GetApplicationStatusMethod;
import com.qaprosoft.zafira.api.serviceMetadataController.GetTenancyInfoMethod;
import com.qaprosoft.zafira.api.serviceMetadataController.GetTheVersionValueOrNumberMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ServiceMetadataControllerTest extends ZafiraAPIBaseTest {
    private static final String EXPECTED_STATUS = "UP";

    @Test
    public void testGetTenancyInfo() {
        GetTenancyInfoMethod getTenancyInfoMethod = new GetTenancyInfoMethod();
        apiExecutor.expectStatus(getTenancyInfoMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getTenancyInfoMethod);
        apiExecutor.validateResponse(getTenancyInfoMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetTheVersionValueOrNumber() {
        GetTheVersionValueOrNumberMethod getTenancyInfoMethod = new GetTheVersionValueOrNumberMethod();
        apiExecutor.expectStatus(getTenancyInfoMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getTenancyInfoMethod);
        apiExecutor.validateResponse(getTenancyInfoMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetApplicationStatus() {
        GetApplicationStatusMethod getApplicationStatusMethod = new GetApplicationStatusMethod();
        apiExecutor.expectStatus(getApplicationStatusMethod, HTTPStatusCodeType.OK);
        String actualStatus = apiExecutor.callApiMethod(getApplicationStatusMethod);
        Assert.assertEquals(actualStatus, EXPECTED_STATUS, "Status is not as expected!");
    }
}
