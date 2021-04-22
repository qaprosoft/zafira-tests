package com.qaprosoft.zafira.api.testCase;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostTestCaseMethod extends ZafiraBaseApiMethodWithAuth {

    public PostTestCaseMethod(int testSuiteId) {
        super("api/test_case/_post/rq.json", "api/test_case/_post/rs.json", "api/test_case.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("Project=ZEB");
        addProperty("testSuiteId", String.valueOf(testSuiteId));
    }
}
