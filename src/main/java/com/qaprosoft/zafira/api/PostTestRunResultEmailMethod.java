package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostTestRunResultEmailMethod extends AbstractApiMethodV2 {

    public PostTestRunResultEmailMethod(String accessToken, int testRunId) {
        super("api/test_run/_post/rq_for_email.json", null, "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testRunId));
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
