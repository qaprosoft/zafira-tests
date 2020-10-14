package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.invitation.v1.DeleteInvitationByIdV1Method;
import com.qaprosoft.zafira.api.invitation.v1.GetInvitationByTokenV1Method;
import com.qaprosoft.zafira.api.invitation.v1.GetInvitationsV1Method;
import com.qaprosoft.zafira.api.invitation.v1.PostInviteUserV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.InvitationServiceV1Impl;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class InvitationTest extends ZafiraAPIBaseTest {
    private final static Logger LOGGER = Logger.getLogger(InvitationTest.class);
    private final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
    private final String INVALID_TOKEN = "INVALID_TOKEN";
    private final String EMPTY_TOKEN = "";
    private final String EMPTY_EMAIL = "";
    private final String INVALID_EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15));

    @Test
    public void testGetInvitations() {
        GetInvitationsV1Method getInvitationsV1Method = new GetInvitationsV1Method();
        apiExecutor.expectStatus(getInvitationsV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getInvitationsV1Method);
        apiExecutor.validateResponse(getInvitationsV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetInvitationByToken() {
        String token = new InvitationServiceV1Impl().getInvitationToken(EMAIL);
        GetInvitationByTokenV1Method getInvitationByTokenV1Method = new GetInvitationByTokenV1Method(token);
        apiExecutor.expectStatus(getInvitationByTokenV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getInvitationByTokenV1Method);
        apiExecutor.validateResponse(getInvitationByTokenV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testPostInvitesUser() {
        PostInviteUserV1Method postInviteUserV1Method = new PostInviteUserV1Method(EMAIL);
        apiExecutor.expectStatus(postInviteUserV1Method, HTTPStatusCodeType.ACCEPTED);
        String response = apiExecutor.callApiMethod(postInviteUserV1Method);
        apiExecutor.validateResponse(postInviteUserV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String token = JsonPath.from(response).getString(JSONConstant.INVITES_TOKEN_KEY);
        int actualId = JsonPath.from(response).getInt(JSONConstant.INVITATION_ID_KEY);
        LOGGER.info(String.format("Invite's token: %s", token));
        int expectedId = new InvitationServiceV1Impl().getIdByInvitationToken(token);
        Assert.assertEquals(actualId, expectedId, "Invite was not delivered!");
        new InvitationServiceV1Impl().deleteInviteById(actualId);
    }

    @Test
    public void testDeleteInvitationById() {
        InvitationServiceV1Impl invitationServiceV1Impl = new InvitationServiceV1Impl();
        int inviteId = invitationServiceV1Impl.getInviteId(EMAIL);
        DeleteInvitationByIdV1Method deleteInvitationByIdV1Method = new DeleteInvitationByIdV1Method(inviteId);
        apiExecutor.expectStatus(deleteInvitationByIdV1Method, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteInvitationByIdV1Method);
        List<Integer> responseAfterDelete = invitationServiceV1Impl.getInvitations();
        LOGGER.info(String.format("Invite's token: %s", responseAfterDelete));
        Assert.assertFalse(responseAfterDelete.contains(String.valueOf(inviteId)), "Invite was not delete!");
    }

    @Test
    public void testGetInvitationByIncorrectToken() {
        GetInvitationByTokenV1Method getInvitationByTokenV1Method = new GetInvitationByTokenV1Method(INVALID_TOKEN);
        apiExecutor.expectStatus(getInvitationByTokenV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getInvitationByTokenV1Method);
    }

    @Test
    public void testGetInvitationByEmptyToken() {
        GetInvitationByTokenV1Method getInvitationByTokenV1Method = new GetInvitationByTokenV1Method(EMPTY_TOKEN);
        apiExecutor.expectStatus(getInvitationByTokenV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(getInvitationByTokenV1Method);
    }

    @Test
    public void testPostInvitesUserWithEmptyEmail() {
        PostInviteUserV1Method postInviteUserV1Method = new PostInviteUserV1Method(EMPTY_EMAIL);
        apiExecutor.expectStatus(postInviteUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postInviteUserV1Method);
    }

    @Test
    public void testPostInvitesUserWithInvalidEmail() {
        PostInviteUserV1Method postInviteUserV1Method = new PostInviteUserV1Method(INVALID_EMAIL);
        apiExecutor.expectStatus(postInviteUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postInviteUserV1Method);
    }
}
