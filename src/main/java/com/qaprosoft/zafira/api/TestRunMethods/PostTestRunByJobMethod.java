package com.qaprosoft.zafira.api.TestRunMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostTestRunByJobMethod extends ZafiraBaseApiMethodWithAuth {

    public PostTestRunByJobMethod(int testRunId) {
        super("api/test_run/_post/rq_by_job.json", "", "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testRunId));
    }
}
