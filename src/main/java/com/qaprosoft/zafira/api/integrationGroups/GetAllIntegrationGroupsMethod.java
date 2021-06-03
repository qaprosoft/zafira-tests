package com.qaprosoft.zafira.api.integrationGroups;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetAllIntegrationGroupsMethod extends ZafiraBaseApiMethodWithAuth {
    public GetAllIntegrationGroupsMethod() {
        super(null, "api/integrationGroups/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
