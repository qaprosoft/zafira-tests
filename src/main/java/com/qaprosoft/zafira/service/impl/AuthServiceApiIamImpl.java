package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.authIAM.PostRefreshTokenMethodIAM;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.service.AuthServiceApiIAM;

public class AuthServiceApiIamImpl implements AuthServiceApiIAM {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public String getRefreshToken() {
        PostRefreshTokenMethodIAM postRefreshTokenMethodIAM = new PostRefreshTokenMethodIAM();
        String response = apiExecutor.callApiMethod(postRefreshTokenMethodIAM);
        return JsonPath.from(response).get(JSONConstant.AUTH_TOKEN_KEY);
    }
}
