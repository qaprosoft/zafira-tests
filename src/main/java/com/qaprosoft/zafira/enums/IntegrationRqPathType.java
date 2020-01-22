package com.qaprosoft.zafira.enums;

public enum IntegrationRqPathType {

    JIRA("api/integration/_put/rq_for_JIRA.json"),

    TESTRAIL("api/integration/_put/rq_for_TESTRAIL.json"),

    QTEST("api/integration/_put/rq_for_QTEST.json"),

    ZEBRUNNER("api/integration/_put/rq_for_ZEBRUNNER.json"),

    SELENIUM("api/integration/_put/rq_for_SELENIUM.json"),

    BROWSERSTACK("api/integration/_put/rq_for_BROWSERSTACK.json"),

    MCLOUD("api/integration/_put/rq_for_MCLOUD.json"),

    SAUCELABS("api/integration/_put/rq_for_SAUCELABS.json"),

    LAMBDATEST("api/integration/_put/rq_for_LAMBDATEST.json"),

    AMAZON("api/integration/_put/rq_for_AMAZON.json"),

    SLACK("api/integration/_put/rq_for_SLACK.json"),

    RABBITMQ("api/integration/_put/rq_for_RABBITMQ.json"),

    EMAIL("api/integration/_put/rq_for_EMAIL.json");

    private String rqPath;

    IntegrationRqPathType(String rqPath) {
        this.rqPath = rqPath;
    }

    public String getPath() {
        return this.rqPath;
    }

}
