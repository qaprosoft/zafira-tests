package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostTestRunByJobMethod extends AbstractApiMethodV2 {

    public PostTestRunByJobMethod(String accessToken, int testRunId) {
        super("api/test_run/_post/rq_by_job.json", "", "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testRunId));
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
