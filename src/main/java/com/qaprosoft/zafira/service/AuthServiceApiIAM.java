package com.qaprosoft.zafira.service;

public interface AuthServiceApiIAM {
    String getRefreshToken();

    String getPermissionsList();

    String getAuthToken(String login, String password);
}
