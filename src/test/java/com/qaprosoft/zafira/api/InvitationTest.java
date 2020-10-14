package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.invitation.v1.DeleteInvitationByIdV1lMethod;
import com.qaprosoft.zafira.api.invitation.v1.GetInvitationByTokenV1Method;
import com.qaprosoft.zafira.api.invitation.v1.GetInvitationV1Method;
import com.qaprosoft.zafira.api.invitation.v1.PostInvitesUserV1Method;
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


    @Test
    public void testGetInvitation() {
        GetInvitationV1Method getInvitationV1Method = new GetInvitationV1Method();
        apiExecutor.expectStatus(getInvitationV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getInvitationV1Method);
        apiExecutor.validateResponse(getInvitationV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
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
        PostInvitesUserV1Method postInvitesUserV1Method = new PostInvitesUserV1Method(EMAIL);
        apiExecutor.expectStatus(postInvitesUserV1Method, HTTPStatusCodeType.ACCEPTED);
        String response = apiExecutor.callApiMethod(postInvitesUserV1Method);
        apiExecutor.validateResponse(postInvitesUserV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
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
        DeleteInvitationByIdV1lMethod deleteInvitationByIdV1lMethod = new DeleteInvitationByIdV1lMethod(inviteId);
        apiExecutor.expectStatus(deleteInvitationByIdV1lMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteInvitationByIdV1lMethod);
        List<Integer> responseAfterDelete = invitationServiceV1Impl.getInvitation();
        LOGGER.info(String.format("Invite's token: %s", responseAfterDelete));
        Assert.assertFalse(responseAfterDelete.contains(String.valueOf(inviteId)), "Invite was not delete!");
    }
}
