package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.manager.APIContextManager;

public class PutTestRunMethod extends ZafiraBaseApiMethodWithAuth {

    public PutTestRunMethod(int testSuiteId, int jobId, int testRunId, String valueToUpdate) {
        super("api/test_run/_put/rq.json", "api/test_run/_put/rs.json", "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("testSuiteId", testSuiteId);
        addProperty("jobId", jobId);
        addProperty("id", testRunId);
        addProperty("workItem", valueToUpdate);
    }
}
