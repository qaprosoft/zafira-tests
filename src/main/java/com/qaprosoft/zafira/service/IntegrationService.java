package com.qaprosoft.zafira.service;

import com.qaprosoft.zafira.enums.IntegrationType;

public interface IntegrationService {
    boolean isIntegrationEnabled(int id, IntegrationType integrationGroup);

    String updateIntegrationInfoById(String rqPath, int integrationId, Boolean enabledType);
}
