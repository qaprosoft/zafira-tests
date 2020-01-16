package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostRetrieveTestBySearchCriteriaMethod extends AbstractApiMethodV2 {

    public PostRetrieveTestBySearchCriteriaMethod(String accessToken, int testRunId) {
        super("api/test/_post/rq_for_retrieve_test.json", "api/test/_post/rs_for_retrieve_test.json",
                "api/test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("Authorization=Bearer " + accessToken);
        addProperty("testRunId", testRunId);
    }
}
