package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.manager.APIContextManager;

public class PostAbortTestRunMethod extends ZafiraBaseApiMethodWithAuth {
    public PostAbortTestRunMethod(int testRunId, String ciRunId) {
        super("api/test_run/_post/rq_to_abort.json", "", "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testRunId));
        replaceUrlPlaceholder("ciRunId", ciRunId);
    }
}
