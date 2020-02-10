package com.qaprosoft.zafira.service;

public interface UserServiceAPI {

    String getUserByCriteria(String query);

    int getUserId(String username);

    void deleteUserFromGroup(int groupId, int userId);
}
