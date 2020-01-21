package com.qaprosoft.zafira.enums;

public enum IntegrationRqPath {

    JIRA("api/integration/_put/rq_for_JIRA.json"),

    TESTRAIL("api/integration/_put/rq_for_TESTRAIL.json"),

    QTEST("api/integration/_put/rq_for_QTEST.json"),

    ZEBRUNNER("api/integration/_put/rq_for_ZEBRUNNER.json");

    private String rqPath;

    IntegrationRqPath(String rqPath) {
        this.rqPath = rqPath;
    }

    public String getPath() {
        return this.rqPath;
    }

}
