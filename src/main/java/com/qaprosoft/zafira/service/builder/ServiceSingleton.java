//package com.qaprosoft.zafira.service.builder;
//
//import com.qaprosoft.carina.core.foundation.utils.Configuration;
//import com.qaprosoft.zafira.client.ZafiraClient;
//import com.qaprosoft.zafira.client.impl.ZafiraClientImpl;
//import com.qaprosoft.zafira.domain.Config;
//import com.qaprosoft.zafira.models.dto.auth.AuthTokenType;
//import com.qaprosoft.zafira.util.http.HttpClient;
//
//public enum ServiceSingleton {
//
//    INSTANCE;
//
//    private final ZafiraClient zafiraClient;
//    private String authToken;
//
//    ServiceSingleton() {
//        HttpClient.Response<AuthTokenType> auth = null;
//        try {
//            String serviceUrl = Configuration.getEnvArg("api_url");
//            String username = Config.ADMIN_USERNAME.getValue();
//            String password = Config.ADMIN_PASSWORD.getValue();
//            zafiraClient = new ZafiraClientImpl(serviceUrl);
//            HttpClient.Response<AuthTokenType> authTokenTypeResponse = zafiraClient.login(username, password);
//            if (zafiraClient.isAvailable()) {
//                String authToken = authTokenTypeResponse.getObject().getAccessToken();
//                String authType = authTokenTypeResponse.getObject().getType();
//                this.authToken = authType + " " + authToken;
//                auth = zafiraClient.refreshToken(authToken);
//            }
//            if (auth != null && auth.getStatus() == 200) {
//                zafiraClient.setAuthToken(auth.getObject().getType() + " " + auth.getObject().getAccessToken());
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage(), e);
//        }
//    }
//
//    public ZafiraClient getClient() {
//        return zafiraClient;
//    }
//
//    public String getAuthToken() {
//        return authToken;
//    }
//
//}
