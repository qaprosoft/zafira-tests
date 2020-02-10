package com.qaprosoft.zafira.api.testController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostLinkWorkItemMethod extends ZafiraBaseApiMethodWithAuth {

    public PostLinkWorkItemMethod(int testCaseId, String jiraId, int testId, String workItemType) {
        super("api/test/_post/rq_for_link.json", null, "api/test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testId));
        addProperty("testCaseId", testCaseId);
        addProperty("jiraId", jiraId);
        addProperty("type", workItemType);
    }
}
