package com.qaprosoft.zafira.service.projectIntegrations;

import java.io.UnsupportedEncodingException;

public interface BrowserStackIntegrationService {

    void addIntegration(int projectId);

    Boolean getEnabledBrowserStackIntegration(int projectId) throws UnsupportedEncodingException;
}
