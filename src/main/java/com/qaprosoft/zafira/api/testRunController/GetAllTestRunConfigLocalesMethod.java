package com.qaprosoft.zafira.api.testRunController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetAllTestRunConfigLocalesMethod extends ZafiraBaseApiMethodWithAuth {

    public GetAllTestRunConfigLocalesMethod() {
        super(null, "api/test_run/_get/rs_all_locales.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
