package com.qaprosoft.zafira.api.serviceMetadataController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetTheVersionValueOrNumberMethod extends ZafiraBaseApiMethodWithAuth {
    public GetTheVersionValueOrNumberMethod() {
        super(null, "api/serviceMetadataController/_get/rs_for_version_value.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
