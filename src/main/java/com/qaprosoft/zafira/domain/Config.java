package com.qaprosoft.zafira.domain;

import com.qaprosoft.carina.core.foundation.utils.R;

public enum Config {

    ADMIN_USERNAME("admin.username"),
    ADMIN_PASSWORD("admin.password"),
    ADMIN_ID("admin.id"),

    DEFAULT_DASHBOARD_ID("dashboard.default.id"),
    DEFAULT_DASHBOARD_NAME("dashboard.default.name");

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

    public long getLongValue() {
        return R.TESTDATA.getLong(key);
    }
}
