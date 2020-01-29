package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.GetAllGroupsMethod;
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
}
