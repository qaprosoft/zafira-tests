package com.qaprosoft.zafira.domain;

import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.util.ConfigXmlUtil;

public enum Config {

    ADMIN_USERNAME("admin.username"),
    ADMIN_PASSWORD("admin.password"),
    ADMIN_ID("admin.id"),

    DEFAULT_DASHBOARD_ID("dashboard.default.id"),
    DEFAULT_DASHBOARD_NAME("dashboard.default.name"),
    PERFORMANCE_DASHBOARD_ID("dashboard.performance.id"),

    CONFIG_XML_APP_VERSION("app_version"),
    CONFIG_XML_ENVIRONMENT("env"),
    CONFIG_XML_BROWSER("browser");

    private final String key;

    Config(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return R.TESTDATA.get(key);
    }

    public String getConfigXmlValue() {
        return ConfigXmlUtil.getConfigXmlValue(key);
    }

    public long getLongValue() {
        return R.TESTDATA.getLong(key);
    }

}
