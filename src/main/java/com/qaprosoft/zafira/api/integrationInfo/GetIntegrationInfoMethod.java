package com.qaprosoft.zafira.api.integrationInfo;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetIntegrationInfoMethod extends ZafiraBaseApiMethodWithAuth {

    public GetIntegrationInfoMethod() {
        super(null, "api/integration_info/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
