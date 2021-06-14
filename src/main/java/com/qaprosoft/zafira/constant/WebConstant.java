package com.qaprosoft.zafira.constant;

import com.qaprosoft.carina.core.foundation.utils.R;

public class WebConstant {
    public static String TENANT_EMAIL = "tenant_email";
    public static String TENANT_NAME = "tenant_name";
    public static String USER_LOGIN = "user_login";
    public static String USER_PASSWORD = "user_password";
    public static int TIME_TO_LOAD_PAGE = R.TESTDATA.getInt(ConfigConstant.TIME_TO_LOAD_PAGE);

    private WebConstant() {
    }
}
