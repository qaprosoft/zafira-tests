package com.qaprosoft.zafira.api.testSuiteController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class GetTestSuiteMethod extends ZafiraBaseApiMethodWithAuth {

    public GetTestSuiteMethod(int id) {
        super(null, "api/test_suite/_get/rs.json", "api/test_suite.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(id));
    }
}
