package com.qaprosoft.zafira.enums;

public enum TestCaseManagementLabels {

    TESTRAIL_PROJECT_ID("com.zebrunner.app/tcm.testrail.project-id"),
    TESTRAIL_SUITE_ID("com.zebrunner.app/tcm.testrail.suite-id"),
    TESTRAIL_TESTCASE_ID("com.zebrunner.app/tcm.testrail.testcase-id");

    private String labelName;

    TestCaseManagementLabels(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelName(){
        return this.labelName;
    }
}
