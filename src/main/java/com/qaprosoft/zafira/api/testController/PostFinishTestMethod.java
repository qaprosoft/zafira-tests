package com.qaprosoft.zafira.api.testController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostFinishTestMethod extends ZafiraBaseApiMethodWithAuth {

    public PostFinishTestMethod(int testCaseId, int testRunId, int testId) {
        super("api/test/_post/rq_to_finish.json", "api/test/_post/rs_to_finish.json", "api/test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testId));
        addProperty("testCaseId", testCaseId);
        addProperty("testRunId", testRunId);
    }
}
