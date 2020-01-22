package com.qaprosoft.zafira.api.IntegrationMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetAllIntegrationsMethod extends ZafiraBaseApiMethodWithAuth {
    public GetAllIntegrationsMethod() {
        super(null, "api/integration/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
