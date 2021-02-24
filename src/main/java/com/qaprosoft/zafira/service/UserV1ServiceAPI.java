package com.qaprosoft.zafira.service;

import java.util.List;

public interface UserV1ServiceAPI {

    int getUserId(String username);

    String create(String username, String password, String email);

    int createAndGetId(String username, String password, String email);

    void deleteUserFromGroup(int groupId, int userId);

    List<Integer> getAllUserGroupIds( int userId);

    String getEmail(String username);

    String getStatus(String username);

    void deleteUserById(int userId);
}
