package com.qaprosoft.zafira.api.testRun;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostRerunTestRunJobsMethod extends ZafiraBaseApiMethodWithAuth {
    public PostRerunTestRunJobsMethod(boolean rerunFailures) {
        super("api/test_run/_post/rq_to_rerun_job.json", "", "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("rerunFailures", String.valueOf(rerunFailures));
    }
}
