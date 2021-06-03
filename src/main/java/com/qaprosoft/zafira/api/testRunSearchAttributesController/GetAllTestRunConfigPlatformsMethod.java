package com.qaprosoft.zafira.api.testRunSearchAttributesController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetAllTestRunConfigPlatformsMethod extends ZafiraBaseApiMethodWithAuth {

    public GetAllTestRunConfigPlatformsMethod() {
        super(null, "api/testRunSearchAttributesController/_get/rs_all_platforms.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
