package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostTestCaseMethod extends AbstractApiMethodV2 {

    public PostTestCaseMethod(String accessToken, int testSuiteId) {
        super("api/test_case/_post/rq.json", "api/test_case/_post/rs.json", "api/test_case.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("Authorization=Bearer " + accessToken);
        setHeaders("Project=UNKNOWN");
        addProperty("testSuiteId", testSuiteId);
    }
}
