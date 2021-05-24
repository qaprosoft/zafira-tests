package com.qaprosoft.zafira.service;

import java.util.List;

public interface AuthServiceApiIAM {
    String getRefreshToken();

    String getPermissionsList();

    List getUserPermissionsList();

    String getAuthToken(String login, String password);

    String getTenantName();
}
