package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetIntegrationInfoMethod extends AbstractApiMethodV2 {

    public GetIntegrationInfoMethod(String accessToken) {
        super(null, "api/integration_info/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
