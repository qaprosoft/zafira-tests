package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.invitation.DeleteInvitationByEmailMethod;
import com.qaprosoft.zafira.api.invitation.GetInvitationByKeywordMethod;
import com.qaprosoft.zafira.api.invitation.PostInvitesUserMethod;
import com.qaprosoft.zafira.api.invitation.v1.DeleteInvitationByIdV1lMethod;
import com.qaprosoft.zafira.api.invitation.v1.GetInvitationByTokenV1Method;
import com.qaprosoft.zafira.api.invitation.v1.GetInvitationV1Method;
import com.qaprosoft.zafira.api.invitation.v1.PostInvitesUserV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.InvitationService;
import com.qaprosoft.zafira.service.InvitationServiceV1;

import java.util.List;

public class InvitationServiceV1Impl implements InvitationServiceV1 {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();


    @Override
    public String postInvite(String email) {
        PostInvitesUserV1Method postInvitesUserV1Method = new PostInvitesUserV1Method(email);
        apiExecutor.expectStatus(postInvitesUserV1Method, HTTPStatusCodeType.ACCEPTED);
        return apiExecutor.callApiMethod(postInvitesUserV1Method);
    }

    @Override
    public int getInviteId(String email) {
        PostInvitesUserV1Method postInvitesUserV1Method = new PostInvitesUserV1Method(email);
        apiExecutor.expectStatus(postInvitesUserV1Method, HTTPStatusCodeType.ACCEPTED);
        String response = apiExecutor.callApiMethod(postInvitesUserV1Method);
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
        PostInvitesUserV1Method postInvitesUserV1Method = new PostInvitesUserV1Method(email);
        apiExecutor.expectStatus(postInvitesUserV1Method, HTTPStatusCodeType.ACCEPTED);
        String response = apiExecutor.callApiMethod(postInvitesUserV1Method);
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
        GetInvitationV1Method getInvitationV1Method = new GetInvitationV1Method();
        apiExecutor.expectStatus(getInvitationV1Method, HTTPStatusCodeType.OK);
        String response=apiExecutor.callApiMethod(getInvitationV1Method);
        return JsonPath.from(response).getList(JSONConstant.ALL_ID_FROM_RESULTS_V1_KEY);
    }
}
