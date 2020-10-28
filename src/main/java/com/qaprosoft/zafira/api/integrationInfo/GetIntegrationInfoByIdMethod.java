package com.qaprosoft.zafira.api.integrationInfo;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetIntegrationInfoByIdMethod extends ZafiraBaseApiMethodWithAuth {
    public GetIntegrationInfoByIdMethod(int id, String integrationGroup) {
        super(null, "api/integration_info/_get/rs_for_integration_by_id.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(id));
        replaceUrlPlaceholder("integrationGroup", integrationGroup);
    }
}
