package com.qaprosoft.zafira.api.serviceMetadataController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetApplicationStatusMethod extends ZafiraBaseApiMethodWithAuth {
    public GetApplicationStatusMethod() {
        super(null, null, new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
