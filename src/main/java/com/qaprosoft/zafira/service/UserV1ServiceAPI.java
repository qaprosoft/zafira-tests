package com.qaprosoft.zafira.service;

import java.util.List;

public interface UserV1ServiceAPI {

    int getUserId(String username);

    String create(String username, String password);

    void deleteUserFromGroup(int groupId, int userId);

    public List<Integer> getAllUserGroupIds( int userId);
}
