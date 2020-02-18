package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.manager.APIContextManager;

public class PostTestCaseMethod extends ZafiraBaseApiMethodWithAuth {

    public PostTestCaseMethod(int testSuiteId) {
        super("api/test_case/_post/rq.json", "api/test_case/_post/rs.json", "api/test_case.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("Project=UNKNOWN");
        addProperty("testSuiteId", String.valueOf(testSuiteId));
    }
}
