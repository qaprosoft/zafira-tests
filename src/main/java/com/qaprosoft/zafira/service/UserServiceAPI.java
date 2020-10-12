package com.qaprosoft.zafira.service;

public interface UserServiceAPI {

    String getUserByCriteria(String query);

    int create(String username);

    void deleteUserFromGroup(int groupId, int userId);
}
