package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.AuthMethods.GetApiTokenMethod;
import com.qaprosoft.zafira.api.AuthMethods.PostGenerateAuthTokenMethod;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class AuthTest extends ZariraAPIBaseTest {

    @Test
    public void testGenerateAuthToken() {
        String username = R.TESTDATA.get(ConfigConstant.AUTH_USERNAME_KEY);
        String password = R.TESTDATA.get(ConfigConstant.AUTTH_PASSWORD_KEY);
        PostGenerateAuthTokenMethod postGenerateAuthTokenMethod = new PostGenerateAuthTokenMethod(username, password);
        apiExecutor.expectStatus(postGenerateAuthTokenMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postGenerateAuthTokenMethod);
        apiExecutor.validateResponse(postGenerateAuthTokenMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test(description = "invalid password")
    public void testGenerateAuthTokenNegative() {
        String username = R.TESTDATA.get(ConfigConstant.AUTH_USERNAME_KEY);
        String InvalidPassword = "test";
        PostGenerateAuthTokenMethod postGenerateAuthTokenMethod = new PostGenerateAuthTokenMethod(username, InvalidPassword);
        apiExecutor.expectStatus(postGenerateAuthTokenMethod, HTTPStatusCodeType.UNAUTHORIZED);
        apiExecutor.callApiMethod(postGenerateAuthTokenMethod);
    }

    @Test
    public void testGenerateApiToken(){
        GetApiTokenMethod getApiTokenMethod = new GetApiTokenMethod();
        apiExecutor.expectStatus(getApiTokenMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getApiTokenMethod);
        apiExecutor.validateResponse(getApiTokenMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
