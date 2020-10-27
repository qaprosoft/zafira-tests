package com.qaprosoft.zafira.api.serviceMetadataController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetTenancyInfoMethod extends ZafiraBaseApiMethodWithAuth {
    public GetTenancyInfoMethod() {
        super(null, "api/serviceMetadataController/_get/rs_for_tenancy_info.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
