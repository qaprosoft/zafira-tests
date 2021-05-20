package com.qaprosoft.zafira.api.testController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostCreateBatchWorkItemsMethod extends ZafiraBaseApiMethodWithAuth {

    public PostCreateBatchWorkItemsMethod(int tesRuntId, int testId, String jiraId) {
        super("api/test/_post/rq_to_create_workItem.json", "api/test/_post/rs_to_create_workItem.json",
                "api/test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(tesRuntId));
        addProperty("jiraId", jiraId);
        addProperty("testId", testId);
    }
}
