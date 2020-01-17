package com.qaprosoft.zafira.enums;

public enum IntegrationGroupType {
    MAIL("MAIL");

    private String group;

    IntegrationGroupType(String group) {
        this.group = group;
    }

    public String getGroup() {
        return this.group;
    }
}
