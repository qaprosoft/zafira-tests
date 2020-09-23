package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.crypto.CryptoTool;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.authIAM.GetAccessTokenMethod;
import com.qaprosoft.zafira.api.authIAM.PostGenerateAuthTokenMethodIAM;
import com.qaprosoft.zafira.api.authIAM.PostRefreshTokenMethodIAM;

import com.qaprosoft.zafira.api.authIAM.PostVerifyPermissionsMethodIAM;


import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.InvitationServiceImpl;
import com.qaprosoft.zafira.util.CryptoUtil;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AuthIAMTest extends ZafiraAPIBaseTest {
    private final static Logger LOGGER = Logger.getLogger(AuthIAMTest.class);
    private final static String EMAIL = R.TESTDATA.get(ConfigConstant.TEST_EMAIL_KEY);

    @Test
    public void testGenerateAuthToken() {
       String username = CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.AUTH_USERNAME_KEY));
       String password = CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.AUTTH_PASSWORD_KEY));
       PostGenerateAuthTokenMethodIAM postGenerateAuthTokenMethodIAM
                = new PostGenerateAuthTokenMethodIAM(username, password);
        apiExecutor.expectStatus(postGenerateAuthTokenMethodIAM, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postGenerateAuthTokenMethodIAM);
        apiExecutor.validateResponse(postGenerateAuthTokenMethodIAM, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test(description = "invalid password")
    public void testGenerateAuthTokenNegative() {
        String username = CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.AUTH_USERNAME_KEY));
        String InvalidPassword = "test";
        PostGenerateAuthTokenMethodIAM postGenerateAuthTokenMethodIAM
                = new PostGenerateAuthTokenMethodIAM(username, InvalidPassword);
        apiExecutor.expectStatus(postGenerateAuthTokenMethodIAM, HTTPStatusCodeType.UNAUTHORIZED);
        apiExecutor.callApiMethod(postGenerateAuthTokenMethodIAM);
    }

    @Test
    public void testRefreshToken() {

        PostRefreshTokenMethodIAM postRefreshTokenMethodIAM
                = new PostRefreshTokenMethodIAM();
        apiExecutor.expectStatus(postRefreshTokenMethodIAM, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postRefreshTokenMethodIAM);
        apiExecutor.validateResponse(postRefreshTokenMethodIAM, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGenerateApiToken() {
        GetAccessTokenMethod getAccessTokenMethod = new GetAccessTokenMethod();
        apiExecutor.expectStatus(getAccessTokenMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAccessTokenMethod);
        apiExecutor.validateResponse(getAccessTokenMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testVerifyPermissions() {
        PostVerifyPermissionsMethodIAM postVerifyPermissionsMethodIAM
                = new PostVerifyPermissionsMethodIAM();
        apiExecutor.expectStatus(postVerifyPermissionsMethodIAM, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postVerifyPermissionsMethodIAM);
    }
}
