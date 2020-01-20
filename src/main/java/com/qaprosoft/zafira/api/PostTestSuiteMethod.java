package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.manager.APIContextManager;

public class PostTestSuiteMethod extends ZafiraBaseApiMethodWithAuth {

    public PostTestSuiteMethod() {
        super("api/test_suite/_post/rq.json", "api/test_suite/_post/rs.json", "api/test_suite.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
