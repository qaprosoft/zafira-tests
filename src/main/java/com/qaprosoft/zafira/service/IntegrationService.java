package com.qaprosoft.zafira.service;

import com.qaprosoft.zafira.enums.IntegrationGroupType;

public interface IntegrationService {
    boolean isIntegrationEnabled(int id, IntegrationGroupType integrationGroup);
}
