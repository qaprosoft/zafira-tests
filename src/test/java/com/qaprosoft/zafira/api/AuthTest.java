package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.AuthMethods.GetApiTokenMethod;
import com.qaprosoft.zafira.api.AuthMethods.PostGenerateAuthTokenMethod;
import com.qaprosoft.zafira.api.AuthMethods.PostNewUserMethod;
import com.qaprosoft.zafira.api.AuthMethods.PostSendResetPasswordEmailMethod;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.AuthServiceAPIImpl;
import com.qaprosoft.zafira.service.impl.InvitationServiceImpl;
import com.qaprosoft.zafira.service.impl.UserServiceAPIImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthTest extends ZafiraAPIBaseTest {
    private final static Logger LOGGER = Logger.getLogger(AuthTest.class);

    @BeforeTest
    public void deleteInviteBefore() {
        String email = R.TESTDATA.get(ConfigConstant.TEST_EMAIL_KEY);
        new InvitationServiceImpl().deleteInviteByEmail(email);
    }

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
    public void testGenerateApiToken() {
        GetApiTokenMethod getApiTokenMethod = new GetApiTokenMethod();
        apiExecutor.expectStatus(getApiTokenMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getApiTokenMethod);
        apiExecutor.validateResponse(getApiTokenMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testRegesterNewUser() {
        String email = R.TESTDATA.get(ConfigConstant.TEST_EMAIL_KEY);
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String time = parser.format(new Date());
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String emailGenerate = "TEST_".concat(RandomStringUtils.randomAlphabetic(15).concat("@gmail.com"));

        String inviteRs = new InvitationServiceImpl().postInvite(email, time);
        String token = JsonPath.from(inviteRs).getString(JSONConstant.INVITES_TOKEN_KEY);
        PostNewUserMethod postNewUserMethod = new PostNewUserMethod(token, username, emailGenerate);
        apiExecutor.expectStatus(postNewUserMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postNewUserMethod);

        String newUserRs = new UserServiceAPIImpl().getUserByCriteria(emailGenerate);
        new InvitationServiceImpl().deleteInviteByEmail(email);
        Assert.assertTrue(newUserRs.contains(emailGenerate), "User was not register!");
    }

    @Test
    public void testSendResetPasswordEmail() {
        String email = R.TESTDATA.get(ConfigConstant.TEST_EMAIL_KEY);
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String time = parser.format(new Date());
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String emailGenerate = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");

        String inviteRs = new InvitationServiceImpl().postInvite(email, time);
        String token = JsonPath.from(inviteRs).getString(JSONConstant.INVITES_TOKEN_KEY);
        new AuthServiceAPIImpl().registerNewUser(token, username, emailGenerate);

        PostSendResetPasswordEmailMethod postSendResetPasswordEmailMethod = new PostSendResetPasswordEmailMethod(emailGenerate);
        apiExecutor.expectStatus(postSendResetPasswordEmailMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postSendResetPasswordEmailMethod);
        new InvitationServiceImpl().deleteInviteByEmail(email);
    }
}
