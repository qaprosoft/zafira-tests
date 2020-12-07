package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.groupIAM.DeleteGroupByIdMethodIAM;
import com.qaprosoft.zafira.api.groupIAM.GetAllGroupsMethodIAM;
import com.qaprosoft.zafira.api.groupIAM.GetGroupByIdMethodIAM;
import com.qaprosoft.zafira.api.groupIAM.PostGroupMethodIAM;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.GroupServiceIAM;
import io.restassured.path.json.JsonPath;

import java.util.List;

public class GroupServiceIamImpl implements GroupServiceIAM {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public List<Integer> getAllGroupsIds() {
        GetAllGroupsMethodIAM getAllGroupsMethod = new GetAllGroupsMethodIAM();
        apiExecutor.expectStatus(getAllGroupsMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(getAllGroupsMethod);
        return JsonPath.from(response).getList(JSONConstant.ALL_GROUPS_ID);
    }

    @Override
    public String getAllGroupsString() {
        GetAllGroupsMethodIAM getAllGroupsMethod = new GetAllGroupsMethodIAM();
        apiExecutor.expectStatus(getAllGroupsMethod, HTTPStatusCodeType.OK);
        return apiExecutor.callApiMethod(getAllGroupsMethod);
    }

    @Override
    public String getGroupById(int groupId) {
        GetGroupByIdMethodIAM getGroupByIdMethod = new GetGroupByIdMethodIAM(groupId);
        apiExecutor.expectStatus(getGroupByIdMethod, HTTPStatusCodeType.OK);
        return apiExecutor.callApiMethod(getGroupByIdMethod);
    }

    @Override
    public int getGroupId(String groupName) {
        PostGroupMethodIAM postGroupMethod = new PostGroupMethodIAM(groupName);
        String response = apiExecutor.callApiMethod(postGroupMethod);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void deleteGroupById(int groupId) {
        DeleteGroupByIdMethodIAM deleteGroupByIdMethod = new DeleteGroupByIdMethodIAM(groupId);
        apiExecutor.callApiMethod(deleteGroupByIdMethod);
    }

    @Override
    public String createGroup(String groupName) {
        PostGroupMethodIAM postGroupMethod = new PostGroupMethodIAM(groupName);
        String response = apiExecutor.callApiMethod(postGroupMethod);
        return response;
    }
}
