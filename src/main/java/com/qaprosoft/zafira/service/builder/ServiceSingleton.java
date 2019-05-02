package com.qaprosoft.zafira.service.builder;

import com.qaprosoft.carina.core.foundation.utils.Configuration;
import com.qaprosoft.zafira.client.ZafiraClient;
import com.qaprosoft.zafira.domain.Config;
import com.qaprosoft.zafira.models.dto.auth.AuthTokenType;

public enum ServiceSingleton {

    INSTANCE;

    private ZafiraClient zafiraClient;

    ServiceSingleton() {
        ZafiraClient.Response<AuthTokenType> auth = null;
        try {
            String serviceUrl = Configuration.getEnvArg("api_url");
            String username = Config.ADMIN_USERNAME.getValue();
            String password = Config.ADMIN_PASSWORD.getValue();
            zafiraClient = new ZafiraClient(serviceUrl);
            ZafiraClient.Response<AuthTokenType> authTokenTypeResponse = zafiraClient.login(username, password);
            if (zafiraClient.isAvailable()) {
                auth = zafiraClient.refreshToken(authTokenTypeResponse.getObject().getAccessToken());
            }
            if (auth != null && auth.getStatus() == 200) {
                zafiraClient.setAuthToken(auth.getObject().getType() + " " + auth.getObject().getAccessToken());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public ZafiraClient getClient() {
        return zafiraClient;
    }
}
