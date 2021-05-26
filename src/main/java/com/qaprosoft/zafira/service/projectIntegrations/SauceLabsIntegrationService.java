package com.qaprosoft.zafira.service.projectIntegrations;

import java.io.UnsupportedEncodingException;

public interface SauceLabsIntegrationService {
    void addIntegration(int projectId);

    Boolean getEnabledSauceLabsIntegration(int projectId) throws UnsupportedEncodingException;

    void deleteSauceLabsIntegration(int projectId);
}
