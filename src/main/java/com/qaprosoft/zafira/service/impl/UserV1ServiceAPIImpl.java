package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.user.DeleteUserFromGroupMethod;
import com.qaprosoft.zafira.api.user.PostSearchUserByCriteriaMethod;
import com.qaprosoft.zafira.api.user.v1.GetUserByUsernameV1Method;
import com.qaprosoft.zafira.api.user.v1.PostUserMethodV1;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.UserV1ServiceAPI;

public class UserV1ServiceAPIImpl implements UserV1ServiceAPI {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public int getUserId(String username) {
        GetUserByUsernameV1Method getUserByUsernameV1Method = new GetUserByUsernameV1Method(username);
        apiExecutor.expectStatus(getUserByUsernameV1Method, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(getUserByUsernameV1Method);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public String create(String username) {
        PostUserMethodV1 postUserMethodV1 = new PostUserMethodV1(username);
        apiExecutor.expectStatus(postUserMethodV1, HTTPStatusCodeType.CREATED);
        String response = apiExecutor.callApiMethod(postUserMethodV1);
        return JsonPath.from(response).getString(JSONConstant.USERNAME_KEY);
    }

    @Override
    public void deleteUserFromGroup(int groupId, int userId) {
        DeleteUserFromGroupMethod deleteUserFromGroupMethod = new DeleteUserFromGroupMethod(groupId, userId);
        apiExecutor.expectStatus(deleteUserFromGroupMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteUserFromGroupMethod);
    }
}
