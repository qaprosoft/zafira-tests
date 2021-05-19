package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.authIAM.PostGenerateAuthTokenMethodIAM;
import com.qaprosoft.zafira.api.authIAM.PostRefreshTokenMethodIAM;
import com.qaprosoft.zafira.api.permissions.GetAllPermissionsMethodIAM;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.AuthServiceApiIAM;
import io.restassured.path.json.JsonPath;

import java.util.List;

public class AuthServiceApiIamImpl implements AuthServiceApiIAM {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public String getRefreshToken() {
        PostRefreshTokenMethodIAM postRefreshTokenMethodIAM = new PostRefreshTokenMethodIAM();
        String response = apiExecutor.callApiMethod(postRefreshTokenMethodIAM);
        return JsonPath.from(response).get(JSONConstant.AUTH_TOKEN_KEY);
    }

    @Override
    public String getPermissionsList() {
        GetAllPermissionsMethodIAM getAllPermissionsMethod = new GetAllPermissionsMethodIAM();
        apiExecutor.expectStatus(getAllPermissionsMethod, HTTPStatusCodeType.OK);
        String permissionsList = apiExecutor.callApiMethod(getAllPermissionsMethod);
        return permissionsList;
    }

    @Override
    public List getUserPermissionsList() {
        PostRefreshTokenMethodIAM postRefreshTokenMethodIAM = new PostRefreshTokenMethodIAM();
        String response = apiExecutor.callApiMethod(postRefreshTokenMethodIAM);
       List userPermissions = JsonPath.from(response).get("permissionsSuperset");
        return userPermissions;
    }

    @Override
    public String getAuthToken(String login, String password) {
        PostGenerateAuthTokenMethodIAM postGenerateAuthTokenMethodIAM
                = new PostGenerateAuthTokenMethodIAM(login, password);
        apiExecutor.expectStatus(postGenerateAuthTokenMethodIAM, HTTPStatusCodeType.OK);
        String response=apiExecutor.callApiMethod(postGenerateAuthTokenMethodIAM);
        return JsonPath.from(response).get(JSONConstant.AUTH_TOKEN_KEY);
    }
}
