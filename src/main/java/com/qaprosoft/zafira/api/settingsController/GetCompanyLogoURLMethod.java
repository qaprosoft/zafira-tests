package com.qaprosoft.zafira.api.settingsController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetCompanyLogoURLMethod extends ZafiraBaseApiMethodWithAuth {
    public GetCompanyLogoURLMethod() {
        super(null, "api/settingsController/_get/rs_for_logo.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
