package com.qaprosoft.zafira.service;

public interface InvitationService {

    String getInvitations (String keyword);

    String postInvite(String email, String time);

    void deleteInviteByEmail(String email);
}
