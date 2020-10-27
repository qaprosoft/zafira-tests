package com.qaprosoft.zafira.api.tagController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetIntegrationInformationByTagMethod extends ZafiraBaseApiMethodWithAuth {

    public GetIntegrationInformationByTagMethod(String ciRunId, String integrationTag) {
        super(null, "api/tagController/_get/rs.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("integrationTag", integrationTag);
        replaceUrlPlaceholder("ciRunId", ciRunId);
    }
}
