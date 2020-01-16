package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostCreateWorkItemMethod extends AbstractApiMethodV2 {

    public PostCreateWorkItemMethod(String accessToken, int testId, String jiraId) {
        super("api/test/_post/rq_to_create_workItem.json", "api/test/_post/rs_to_create_workItem.json",
                "api/test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testId));
        setHeaders("Authorization=Bearer " + accessToken);
        addProperty("jiraId", jiraId);
    }

}
