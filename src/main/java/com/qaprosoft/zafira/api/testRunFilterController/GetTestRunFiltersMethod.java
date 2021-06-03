package com.qaprosoft.zafira.api.testRunFilterController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetTestRunFiltersMethod extends ZafiraBaseApiMethodWithAuth {
    public GetTestRunFiltersMethod() {
        super(null, "api/testRunFilter/_get/rs.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
