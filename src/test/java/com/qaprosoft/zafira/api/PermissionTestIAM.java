package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.permissions.GetAllPermissionsMethodIAM;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class PermissionTestIAM extends ZafiraAPIBaseTest {

    @Test
    public void testGetAllPermissionIAM() {
        GetAllPermissionsMethodIAM getAllPermissionsMethod = new GetAllPermissionsMethodIAM();
        apiExecutor.expectStatus(getAllPermissionsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllPermissionsMethod);
        apiExecutor.validateResponse(getAllPermissionsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
