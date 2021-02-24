package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.user.v1.DeleteUserByIdV1Method;
import io.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.user.DeleteUserFromGroupMethod;
import com.qaprosoft.zafira.api.user.PostSearchUserByCriteriaMethod;
import com.qaprosoft.zafira.api.user.v1.GetUserByIdV1Method;
import com.qaprosoft.zafira.api.user.v1.GetUserByUsernameV1Method;
import com.qaprosoft.zafira.api.user.v1.PostUserMethodV1;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.UserV1ServiceAPI;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.List;

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
    public String create(String username, String password, String email) {
        PostUserMethodV1 postUserMethodV1 = new PostUserMethodV1(username,password,email);
        apiExecutor.expectStatus(postUserMethodV1, HTTPStatusCodeType.CREATED);
        String response = apiExecutor.callApiMethod(postUserMethodV1);
        return JsonPath.from(response).getString(JSONConstant.USERNAME_KEY);
    }

    @Override
    public int createAndGetId(String username, String password, String email) {
        PostUserMethodV1 postUserMethodV1 = new PostUserMethodV1(username,password,email);
        apiExecutor.expectStatus(postUserMethodV1, HTTPStatusCodeType.CREATED);
        String response = apiExecutor.callApiMethod(postUserMethodV1);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void deleteUserFromGroup(int groupId, int userId) {
        DeleteUserFromGroupMethod deleteUserFromGroupMethod = new DeleteUserFromGroupMethod(groupId, userId);
        apiExecutor.expectStatus(deleteUserFromGroupMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteUserFromGroupMethod);
    }

    @Override
    public List<Integer> getAllUserGroupIds(int userId) {
        GetUserByIdV1Method getUserByIdV1Method = new GetUserByIdV1Method(userId);
        apiExecutor.expectStatus(getUserByIdV1Method, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(getUserByIdV1Method);
        return JsonPath.from(response).getList(JSONConstant.ALL_USERS_GROUPS_ID);
    }

    @Override
    public String getEmail(String username) {
        GetUserByUsernameV1Method getUserByUsernameV1Method = new GetUserByUsernameV1Method(username);
        apiExecutor.expectStatus(getUserByUsernameV1Method, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(getUserByUsernameV1Method);
        return JsonPath.from(response).getString(JSONConstant.EMAIL_KEY);
    }

    @Override
    public String getStatus(String username) {
        GetUserByUsernameV1Method getUserByUsernameV1Method = new GetUserByUsernameV1Method(username);
        apiExecutor.expectStatus(getUserByUsernameV1Method, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(getUserByUsernameV1Method);
        return JsonPath.from(response).getString(JSONConstant.STATUS_KEY);
    }

    @Override
    public void deleteUserById( int userId) {
        DeleteUserByIdV1Method getUserByCriteriaV1Method = new DeleteUserByIdV1Method(userId);
       apiExecutor.callApiMethod(getUserByCriteriaV1Method);
    }

}
