package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.user.DeleteUserFromGroupMethod;
import com.qaprosoft.zafira.api.user.PostSearchUserByCriteriaMethod;
import com.qaprosoft.zafira.api.user.PostUserMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.UserServiceAPI;

public class UserServiceAPIImpl implements UserServiceAPI {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public String getUserByCriteria(String query) {
        PostSearchUserByCriteriaMethod postSearchUserByCriteriaMethod = new PostSearchUserByCriteriaMethod(query);
        apiExecutor.expectStatus(postSearchUserByCriteriaMethod, HTTPStatusCodeType.OK);
        return apiExecutor.callApiMethod(postSearchUserByCriteriaMethod);
    }

    @Override
    public int getUserId(String username) {
        PostUserMethod putCreateUserMethod = new PostUserMethod(username);
        apiExecutor.expectStatus(putCreateUserMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(putCreateUserMethod);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void deleteUserFromGroup(int groupId, int userId) {
        DeleteUserFromGroupMethod deleteUserFromGroupMethod = new DeleteUserFromGroupMethod(groupId, userId);
        apiExecutor.expectStatus(deleteUserFromGroupMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteUserFromGroupMethod);
    }
}
