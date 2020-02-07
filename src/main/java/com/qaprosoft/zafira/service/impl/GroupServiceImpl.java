package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.GroupMethods.DeleteGroupByIdMethod;
import com.qaprosoft.zafira.api.GroupMethods.GetAllGroupsMethod;
import com.qaprosoft.zafira.api.GroupMethods.GetGroupByIdMethod;
import com.qaprosoft.zafira.api.GroupMethods.PostGroupMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.GroupService;

public class GroupServiceImpl implements GroupService {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public String getAllGroups() {
        GetAllGroupsMethod getAllGroupsMethod = new GetAllGroupsMethod();
        apiExecutor.expectStatus(getAllGroupsMethod, HTTPStatusCodeType.OK);
        return apiExecutor.callApiMethod(getAllGroupsMethod);
    }

    @Override
    public String getGroupById(int groupId) {
        GetGroupByIdMethod getGroupByIdMethod = new GetGroupByIdMethod(groupId);
        apiExecutor.expectStatus(getGroupByIdMethod, HTTPStatusCodeType.OK);
        return apiExecutor.callApiMethod(getGroupByIdMethod);
    }

    @Override
    public int createGroup(String groupName) {
        PostGroupMethod postGroupMethod = new PostGroupMethod(groupName);
        apiExecutor.expectStatus(postGroupMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postGroupMethod);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void deleteGroupById(int groupId) {
        DeleteGroupByIdMethod deleteGroupByIdMethod = new DeleteGroupByIdMethod(groupId);
        apiExecutor.expectStatus(deleteGroupByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteGroupByIdMethod);
    }

}