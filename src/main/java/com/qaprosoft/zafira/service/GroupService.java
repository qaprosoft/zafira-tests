package com.qaprosoft.zafira.service;

public interface GroupService {

    String getAllGroups();

    String getGroupById(int groupId);

    int createGroup(String groupName);
}
