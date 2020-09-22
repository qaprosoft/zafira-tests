package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.invitation.DeleteInvitationByEmailMethod;
import com.qaprosoft.zafira.api.invitation.GetInvitationByKeywordMethod;
import com.qaprosoft.zafira.api.invitation.PostInvitesUserMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.InvitationService;

public class InvitationServiceImpl implements InvitationService {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public String getInvitations(String keyword) {
        GetInvitationByKeywordMethod getInvitationByKeywordMethod = new GetInvitationByKeywordMethod(keyword);
        apiExecutor.expectStatus(getInvitationByKeywordMethod, HTTPStatusCodeType.OK);
        return apiExecutor.callApiMethod(getInvitationByKeywordMethod);
    }

    @Override
    public String postInvite(String email, String time) {
        PostInvitesUserMethod postInvitesUserMethod = new PostInvitesUserMethod(email, time);
        apiExecutor.expectStatus(postInvitesUserMethod, HTTPStatusCodeType.OK);
        return apiExecutor.callApiMethod(postInvitesUserMethod);
    }

    @Override
    public void deleteInviteByEmail(String email) {
        DeleteInvitationByEmailMethod deleteInvitationByEmailMethod = new DeleteInvitationByEmailMethod(email);
        apiExecutor.expectStatus(deleteInvitationByEmailMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteInvitationByEmailMethod);
    }
}
