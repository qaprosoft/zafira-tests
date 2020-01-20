package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.manager.APIContextManager;

public class PostMarkTestRunReviewedMethod extends ZafiraBaseApiMethodWithAuth {

    public PostMarkTestRunReviewedMethod(int testRunId) {
        super("api/test_run/_post/rq_to_mark_reviewed.json", null, "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testRunId));
    }
}
