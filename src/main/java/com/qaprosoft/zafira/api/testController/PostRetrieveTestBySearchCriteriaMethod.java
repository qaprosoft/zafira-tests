package com.qaprosoft.zafira.api.testController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostRetrieveTestBySearchCriteriaMethod extends ZafiraBaseApiMethodWithAuth {

    public PostRetrieveTestBySearchCriteriaMethod(int testRunId) {
        super("api/test/_post/rq_for_retrieve_test.json", "api/test/_post/rs_for_retrieve_test.json",
                "api/test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("testRunId", String.valueOf(testRunId));
    }
}
