package com.qaprosoft.zafira.domain;

public enum Route {

    SIGNIN("/signin"),
    DASHBOARDS("/dashboards/%d"),
    TEST_RUNS("/tests/runs"),
    TEST_RUNS_ITEM("/tests/runs/%d"),
    USERS("/users/list"),
    GROUPS("/users/groups"),
    INVITATIONS("/users/invitations"),
    MONITORS("/monitors"),
    INTEGRATIONS("/integrations"),
    USER_PROFILE("/profile");

    private final String relativePath;

    Route(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getRelativePath(Object... slices) {
        return String.format(relativePath, slices);
    }
}
