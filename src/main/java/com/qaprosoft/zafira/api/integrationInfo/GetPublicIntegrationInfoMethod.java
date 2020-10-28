package com.qaprosoft.zafira.api.integrationInfo;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetPublicIntegrationInfoMethod extends ZafiraBaseApiMethodWithAuth {

    public GetPublicIntegrationInfoMethod() {
        super(null, "api/integration_info/_get/rs_for_public_integration.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
