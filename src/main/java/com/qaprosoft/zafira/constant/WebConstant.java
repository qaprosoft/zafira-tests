package com.qaprosoft.zafira.constant;

import com.qaprosoft.carina.core.foundation.utils.R;

public class WebConstant {
    public static String TENANT_EMAIL = "tenant_email";
    public static String TENANT_NAME = "tenant_name";
    public static String USER_LOGIN = "user_login";
    public static String USER_PASSWORD = "user_password";
    public static int TIME_TO_LOAD_PAGE = R.TESTDATA.getInt(ConfigConstant.TIME_TO_LOAD_PAGE);
    //#========================== Dashboards(Wed) ========================#
    public static final String MAIN_DASHBOARD_PAGE_TITLE = R.TESTDATA.get(ConfigConstant.MAIN_DASHBOARD_PAGE_TITLE);
    public static final String COLUMN_NAME_DASHBOARD_NAME = R.TESTDATA.get(ConfigConstant.COLUMN_NAME_DASHBOARD_NAME);
    public static final String COLUMN_NAME_CREATION_DATE = R.TESTDATA.get(ConfigConstant.COLUMN_NAME_CREATION_DATE);
    public static final String DASHBOARD_NAME_PERSONAL = R.TESTDATA.get(ConfigConstant.DASHBOARD_NAME_PERSONAL);
    public static final String DASHBOARD_NAME_GENERAL = R.TESTDATA.get(ConfigConstant.DASHBOARD_NAME_GENERAL);
    public static final String DASHBOARD_CREATION_DATE = R.TESTDATA.get(ConfigConstant.DASHBOARD_CREATION_DATE);
}
