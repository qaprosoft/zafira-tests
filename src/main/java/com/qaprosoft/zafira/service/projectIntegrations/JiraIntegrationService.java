package com.qaprosoft.zafira.service.projectIntegrations;

import java.io.UnsupportedEncodingException;

public interface JiraIntegrationService {

    void addIntegration(int projectId);

    //  Boolean getEnabledIntegration(int projectId) throws UnsupportedEncodingException;

    void deleteIntegration(int projectId);
}
