package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class PermissionTest extends ZariraAPIBaseTest {

    @Test
    public void testGetAllPermission(){
        GetAllPermissionsMethod getAllPermissionsMethod = new GetAllPermissionsMethod();
        apiExecutor.expectStatus(getAllPermissionsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllPermissionsMethod);
        apiExecutor.validateResponse(getAllPermissionsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
