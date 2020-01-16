package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostFinishTestRunMethod extends AbstractApiMethodV2 {

    public PostFinishTestRunMethod(String accessToken, int testRunId) {
        super(null, "api/test_run/_post/rs_to_finish.json", "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testRunId));
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
