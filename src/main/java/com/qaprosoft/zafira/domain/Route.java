package com.qaprosoft.zafira.domain;

public enum Route {

    SIGNIN("/signin"),
    DASHBOARDS("/dashboards/%d"),
    TEST_RUNS("/tests/runs"),
    USERS("/users"),
    MONITORS("/monitors"),
    INTEGRATIONS("/integrations"),
    USER_PROFILE("/users/profile");

    private final String relativePath;

    Route(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getRelativePath(Object... slices) {
        return String.format(relativePath, slices);
    }
}
