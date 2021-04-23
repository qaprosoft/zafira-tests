package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.socialController.GetZebrunnerTweetsMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class SocialControllerTest extends ZafiraAPIBaseTest {

    @Test(enabled = false)
    public void testGetTenancyInfo() {
        GetZebrunnerTweetsMethod getZebrunnerTweetsMethod = new GetZebrunnerTweetsMethod();
        apiExecutor.expectStatus(getZebrunnerTweetsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getZebrunnerTweetsMethod);
        apiExecutor.validateResponse(getZebrunnerTweetsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
