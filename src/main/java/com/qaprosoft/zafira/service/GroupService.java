package com.qaprosoft.zafira.service;

import java.util.List;

public interface GroupService {

    List<Integer> getAllGroupsIds();
    String getAllGroupsString();

    String getGroupById(int groupId);

    int createGroup(String groupName);

    void deleteGroupById(int groupId);
}
