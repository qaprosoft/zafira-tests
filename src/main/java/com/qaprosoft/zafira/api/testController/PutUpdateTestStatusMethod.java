package com.qaprosoft.zafira.api.testController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutUpdateTestStatusMethod extends ZafiraBaseApiMethodWithAuth {

    public PutUpdateTestStatusMethod(int testId, int testSuiteId, int jobId, String expectedTestStatusValue) {
        super("api/test/_put/rq.json", "/api/test/_put/rs.json", "api/test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("id", String.valueOf(testId));
        addProperty("testSuiteId", String.valueOf(testSuiteId));
        addProperty("jobId", String.valueOf(jobId));
        addProperty("status", expectedTestStatusValue);
    }
}
