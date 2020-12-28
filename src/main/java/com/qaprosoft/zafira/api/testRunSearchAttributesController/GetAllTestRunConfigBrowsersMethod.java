package com.qaprosoft.zafira.api.testRunSearchAttributesController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetAllTestRunConfigBrowsersMethod extends ZafiraBaseApiMethodWithAuth {

    public GetAllTestRunConfigBrowsersMethod() {
        super(null, "api/testRunSearchAttributesController/_get/rs_all_browsers.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
