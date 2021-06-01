package com.qaprosoft.zafira.service.projectIntegrations;

import java.io.UnsupportedEncodingException;

public interface QTestIntegrationService {

    void addIntegration(int projectId);

    Boolean getEnabledIntegration(int projectId) throws UnsupportedEncodingException;

    void deleteIntegration(int projectId);
}
