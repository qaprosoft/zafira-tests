package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostLinkWorkItemMethod extends AbstractApiMethodV2 {

    public PostLinkWorkItemMethod(String accessToken, int testCaseId, String jiraId, int testId, String workItemType) {
        super("api/test/_post/rq_for_link.json", null, "api/test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testId));
        setHeaders("Authorization=Bearer " + accessToken);
        addProperty("testCaseId", testCaseId);
        addProperty("jiraId", jiraId);
        addProperty("type", workItemType);
    }
}
