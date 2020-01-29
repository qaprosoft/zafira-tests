package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class GroupTest extends ZariraAPIBaseTest {

    @Test
    public void testGetAllGroups(){
        GetAllGroupsMethod getAllGroupsMethod = new GetAllGroupsMethod();
        apiExecutor.expectStatus(getAllGroupsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllGroupsMethod);
        apiExecutor.validateResponse(getAllGroupsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
