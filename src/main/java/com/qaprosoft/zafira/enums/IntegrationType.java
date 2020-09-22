package com.qaprosoft.zafira.enums;

public enum IntegrationType {
    MAIL("MAIL");

    private String integrationType;

    IntegrationType(String integrationType) {
        this.integrationType = integrationType;
    }

    public String getIntegrationType() {
        return this.integrationType;
    }
}
