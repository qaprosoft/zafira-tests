package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.invitation.DeleteInvitationByEmailMethod;
import com.qaprosoft.zafira.api.invitation.GetInvitationByKeywordMethod;
import com.qaprosoft.zafira.api.invitation.PostInvitesUserMethod;
import com.qaprosoft.zafira.api.invitation.PostResendInviteUserMethod;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.domain.EmailMsg;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.EmailManager;
import com.qaprosoft.zafira.service.impl.InvitationServiceImpl;
import com.qaprosoft.zafira.util.CryptoUtil;

import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InvitationTest extends ZafiraAPIBaseTest {
    private final static Logger LOGGER = Logger.getLogger(InvitationTest.class);
    private final EmailManager EMAIL = new EmailManager(
            CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.GMAIL_USERNAME_KEY)),
            CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.GMAIL_PASSWORD_KEY)));

    @BeforeTest (enabled= false)
    public void deleteInviteBefore() {
        String email = R.TESTDATA.get(ConfigConstant.TEST_EMAIL_KEY);
        new InvitationServiceImpl().deleteInviteByEmail(email);
    }

    @Test (enabled= false)
    public void testGetInvitationByKeyword() {
        String keyword = R.TESTDATA.get(ConfigConstant.TEST_EMAIL_KEY);
        GetInvitationByKeywordMethod getInvitationByKeywordMethod = new GetInvitationByKeywordMethod(keyword);
        apiExecutor.expectStatus(getInvitationByKeywordMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getInvitationByKeywordMethod);
        apiExecutor.validateResponse(getInvitationByKeywordMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test (enabled= false)
    public void testPostInvitesUser() {
        Date startTestTime = new Date();
        String email = R.TESTDATA.get(ConfigConstant.TEST_EMAIL_KEY);
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String time = parser.format(startTestTime);

        PostInvitesUserMethod postInvitesUserMethod = new PostInvitesUserMethod(email, time);
        apiExecutor.expectStatus(postInvitesUserMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postInvitesUserMethod);
        apiExecutor.validateResponse(postInvitesUserMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        String token = JsonPath.from(response).getString(JSONConstant.INVITES_TOKEN_KEY);
        LOGGER.info(String.format("Invite's token: %s", token));
        Assert.assertTrue(verifyIfEmailWasDelivered(startTestTime, token), "Invite was not delivered!");
    }

    @Test (enabled= false)
    public void testDeleteInvitationByEmail() {
        String email = R.TESTDATA.get(ConfigConstant.TEST_EMAIL_KEY);
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String time = parser.format(new Date());
        InvitationServiceImpl invitationServiceImpl = new InvitationServiceImpl();
        invitationServiceImpl.postInvite(email, time);

        String response = invitationServiceImpl.getInvitations(email);
        int invitationId = JsonPath.from(response).get(JSONConstant.ALL_ID_FROM_RESULTS_KEY);
        Assert.assertNotEquals(0, invitationId, "Invite was not send!");

        DeleteInvitationByEmailMethod deleteInvitationByEmailMethod = new DeleteInvitationByEmailMethod(email);
        apiExecutor.expectStatus(deleteInvitationByEmailMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteInvitationByEmailMethod);

        String responseAfterDelete = invitationServiceImpl.getInvitations(email);
        Assert.assertFalse(responseAfterDelete.contains(String.valueOf(invitationId)), "Invite was not delete!");
    }

    @Test (enabled= false)
    public void testResendInviteUser() {
        String email = R.TESTDATA.get(ConfigConstant.TEST_EMAIL_KEY);
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String time = parser.format(new Date());
        InvitationServiceImpl invitationServiceImpl = new InvitationServiceImpl();
        invitationServiceImpl.postInvite(email, time);

        PostResendInviteUserMethod postResendInviteUserMethod = new PostResendInviteUserMethod(email, time);
        apiExecutor.expectStatus(postResendInviteUserMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postResendInviteUserMethod);
        apiExecutor.validateResponse(postResendInviteUserMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        invitationServiceImpl.deleteInviteByEmail(email);
        apiExecutor.expectStatus(postResendInviteUserMethod, HTTPStatusCodeType.NOT_FOUND);
        String response = apiExecutor.callApiMethod(postResendInviteUserMethod);
        LOGGER.info(response);
    }

    private boolean verifyIfEmailWasDelivered(Date startTestTime, String token) {
        final int lastEmailIndex = 0;
        final int emailsCount = 1;
        LOGGER.info("Will get last email from inbox.");
        EMAIL.waitForEmailDelivered(startTestTime, token); // decency from connection, wait a little bit
        EmailMsg email = EMAIL.getInbox(emailsCount)[lastEmailIndex];
        return email.getContent().contains(token);
    }

    @AfterMethod
    public void deleteInvite() {
        String email = R.TESTDATA.get(ConfigConstant.TEST_EMAIL_KEY);
        new InvitationServiceImpl().deleteInviteByEmail(email);
    }
}
