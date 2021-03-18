package com.qaprosoft.zafira.enums;

public enum IntegrationProvider {

    ZEBRUNNER("https://hub.zebrunner.com"),

    SELENIUM("http://hub.zebrunner.com"),

    MCLOUD("http://hub.zebrunner.com");


    private String url;

    IntegrationProvider(String url) {
        this.url = url;
    }

    public String getURL() {
        return this.url;
    }
}
