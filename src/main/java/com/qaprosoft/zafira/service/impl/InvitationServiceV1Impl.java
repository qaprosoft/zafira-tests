package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.invitation.v1.DeleteInvitationByIdV1lMethod;
import com.qaprosoft.zafira.api.invitation.v1.GetInvitationByTokenV1Method;
import com.qaprosoft.zafira.api.invitation.v1.GetInvitationsV1Method;
import com.qaprosoft.zafira.api.invitation.v1.PostInviteUserV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.InvitationServiceV1;

import java.util.List;

public class InvitationServiceV1Impl implements InvitationServiceV1 {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();


    @Override
    public String postInvite(String email) {
        PostInviteUserV1Method postInviteUserV1Method = new PostInviteUserV1Method(email);
        apiExecutor.expectStatus(postInviteUserV1Method, HTTPStatusCodeType.ACCEPTED);
        return apiExecutor.callApiMethod(postInviteUserV1Method);
    }

    @Override
    public int getInviteId(String email) {
        PostInviteUserV1Method postInviteUserV1Method = new PostInviteUserV1Method(email);
        apiExecutor.expectStatus(postInviteUserV1Method, HTTPStatusCodeType.ACCEPTED);
        String response = apiExecutor.callApiMethod(postInviteUserV1Method);
        return JsonPath.from(response).getInt(JSONConstant.INVITATION_ID_KEY);
    }

    @Override
    public void deleteInviteById(int id) {
        DeleteInvitationByIdV1lMethod deleteInvitationByIdMethod = new DeleteInvitationByIdV1lMethod(id);
        apiExecutor.expectStatus(deleteInvitationByIdMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteInvitationByIdMethod);
    }

    @Override
    public String getInvitationToken(String email) {
        PostInviteUserV1Method postInviteUserV1Method = new PostInviteUserV1Method(email);
        apiExecutor.expectStatus(postInviteUserV1Method, HTTPStatusCodeType.ACCEPTED);
        String response = apiExecutor.callApiMethod(postInviteUserV1Method);
        JsonPath.from(response).getList(JSONConstant.ID_KEY);
        return JsonPath.from(response).getString(JSONConstant.INVITES_TOKEN_KEY);
    }

    @Override
    public int getIdByInvitationToken(String token) {
        GetInvitationByTokenV1Method getInvitationByTokenV1Method = new GetInvitationByTokenV1Method(token);
        apiExecutor.expectStatus(getInvitationByTokenV1Method, HTTPStatusCodeType.OK);
        String response =  apiExecutor.callApiMethod(getInvitationByTokenV1Method);
                return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }
    @Override
    public List<Integer> getInvitation() {
        GetInvitationsV1Method getInvitationsV1Method = new GetInvitationsV1Method();
        apiExecutor.expectStatus(getInvitationsV1Method, HTTPStatusCodeType.OK);
        String response=apiExecutor.callApiMethod(getInvitationsV1Method);
        return JsonPath.from(response).getList(JSONConstant.ALL_ID_FROM_RESULTS_V1_KEY);
    }
}
