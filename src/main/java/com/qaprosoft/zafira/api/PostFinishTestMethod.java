package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostFinishTestMethod extends AbstractApiMethodV2 {

    public PostFinishTestMethod(String accessToken, int testCaseId, int testRunId, int testId) {
        super("api/test/_post/rq_to_finish.json", "api/test/_post/rs_to_finish.json", "api/test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testId));
        setHeaders("Authorization=Bearer " + accessToken);
        addProperty("testCaseId", testCaseId);
        addProperty("testRunId", testRunId);
    }
}
