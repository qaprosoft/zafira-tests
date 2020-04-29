package com.qaprosoft.zafira.api.integrationInfo;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.enums.IntegrationType;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetIntegrationInfoByIdMethod extends ZafiraBaseApiMethodWithAuth {
    public GetIntegrationInfoByIdMethod(int id, IntegrationType integrationGroup) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(id));
        replaceUrlPlaceholder("integrationGroup", integrationGroup.getIntegrationType());
    }
}
