package com.qaprosoft.zafira.api.testController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutWorkItemMethod extends ZafiraBaseApiMethodWithAuth {

    public PutWorkItemMethod(int testCaseId, String jiraId, int testId, String workItemType) {
        super("api/test/_put/rq_for_update_issue.json", "api/test/_put/rs_for_update_issue.json",
                "api/test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testId));
        addProperty("testCaseId", String.valueOf(testCaseId));
        addProperty("jiraId", jiraId);
        addProperty("type", workItemType);
    }
}
