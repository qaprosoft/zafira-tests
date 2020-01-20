package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.manager.APIContextManager;

class PostCreateWorkItemMethod extends ZafiraBaseApiMethodWithAuth {

    public PostCreateWorkItemMethod(int testId, String jiraId) {
        super("api/test/_post/rq_to_create_workItem.json", "api/test/_post/rs_to_create_workItem.json",
                "api/test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testId));
        addProperty("jiraId", jiraId);
    }

}
