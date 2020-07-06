package com.qaprosoft.zafira.service;

import java.util.List;

public interface GroupServiceIAM {
    List<Integer> getAllGroupsIds();
    String getAllGroupsString();

    String getGroupById(int groupId);

    int getGroupId(String groupName);

    void deleteGroupById(int groupId);

    String createGroup (String groupName);
}
