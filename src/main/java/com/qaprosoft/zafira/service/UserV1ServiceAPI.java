package com.qaprosoft.zafira.service;

public interface UserV1ServiceAPI {

    int getUserId(String username);

    String create(String username);

    void deleteUserFromGroup(int groupId, int userId);
}
