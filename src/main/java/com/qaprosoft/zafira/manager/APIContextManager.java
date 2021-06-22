package com.qaprosoft.zafira.manager;

import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.authIAM.PostGenerateAuthTokenMethodIAM;
import com.qaprosoft.zafira.api.authIAM.PostRefreshTokenMethodIAM;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.service.impl.ExecutionServiceImpl;
import com.qaprosoft.zafira.util.CryptoUtil;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class APIContextManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String accessToken;
    public static String ENV_VALUE = R.CONFIG.get(ConfigConstant.ENV_KEY);
    public static final String BASE_URL = R.CONFIG.get(String.format(ConfigConstant.BASE_URL_KEY, ENV_VALUE));
    public static final String API_URL = R.CONFIG.get(String.format(ConfigConstant.BASE_API_URL_KEY, ENV_VALUE));
    public static final String PROJECT_SERVICE_URL = R.CONFIG.get(String.format(ConfigConstant.PROJECT_SERVICE_URL_KEY, ENV_VALUE));
    public static final String REFRESH_TOKEN = R.CONFIG.get(String.format(ConfigConstant.REFRESH_TOKEN_KEY_KEY, ENV_VALUE));
    public static final int AUTHOMATION_SERVER_ID_VALUE = R.TESTDATA.getInt(ConfigConstant.AUTHOMATION_SERVER_KEY);
    public static final int SCM_ACCOUNT_TYPE_ID_VALUE = R.TESTDATA.getInt(ConfigConstant.SCM_ACCOUNT_TYPE_KEY);
    public static final int EXISTING_SCM_ID = R.TESTDATA.getInt(ConfigConstant.EXISTING_SCM_ID);
    public static final String PROJECT_NAME_KEY = R.TESTDATA.get(ConfigConstant.PROJECT_NAME_KEY);
    private static String username = CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.AUTH_USERNAME_KEY));
    private static String password = CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.AUTTH_PASSWORD_KEY));


    public APIContextManager() {

        LOGGER.info("");
        LOGGER.info("The following env will be used: ".concat(ENV_VALUE));
        LOGGER.info("Base API URL: ".concat(API_URL));
        setAccessToken();
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken() {
        ExecutionServiceImpl executor = new ExecutionServiceImpl();
        String rsString = executor.callApiMethod(new PostGenerateAuthTokenMethodIAM(username, password));
        accessToken = JsonPath.from(rsString).get(JSONConstant.AUTH_TOKEN_KEY);
        LOGGER.info("Zebrunner Access Token: ".concat(accessToken));
    }
}
