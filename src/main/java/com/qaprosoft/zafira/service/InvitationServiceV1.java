package com.qaprosoft.zafira.service;

import java.util.List;

public interface InvitationServiceV1 {

    String postInvite(String email);

    int getInviteId(String email);

    void deleteInviteById(int id);

    String getInvitationToken(String email);

    int getIdByInvitationToken(String token);

    List<Integer> getInvitation();
}
