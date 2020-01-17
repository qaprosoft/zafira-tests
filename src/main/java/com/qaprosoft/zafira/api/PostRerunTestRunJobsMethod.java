package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostRerunTestRunJobsMethod extends AbstractApiMethodV2 {
    public PostRerunTestRunJobsMethod(String accessToken, boolean rerunFailures) {
        super("api/test_run/_post/rq_to_rerun_job.json", "", "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("rerunFailures", String.valueOf(rerunFailures));
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
