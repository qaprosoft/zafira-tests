package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.manager.APIContextManager;

public class PutUpdateTestStatusMethod extends ZafiraBaseApiMethodWithAuth {

    public PutUpdateTestStatusMethod(int testId, int testSuiteId, int jobId, String expectedTestStatusValue) {
        super("api/test/_put/rq.json", "/api/test/_put/rs.json", "api/test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("id", testId);
        addProperty("testSuiteId", testSuiteId);
        addProperty("jobId", jobId);
        addProperty("status", expectedTestStatusValue);
    }
}
