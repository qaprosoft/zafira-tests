package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.auth.PostNewUserMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.AuthServiceAPI;

public class AuthServiceAPIImpl implements AuthServiceAPI {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public void registerNewUser(String token, String username, String email) {
        PostNewUserMethod postNewUserMethod = new PostNewUserMethod(token, username, email);
        apiExecutor.expectStatus(postNewUserMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postNewUserMethod);
    }
}
