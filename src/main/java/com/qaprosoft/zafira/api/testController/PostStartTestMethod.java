package com.qaprosoft.zafira.api.testController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.UUID;

public class PostStartTestMethod extends ZafiraBaseApiMethodWithAuth {

    public PostStartTestMethod(int testCaseId, int testRunId) {
        super("/api/test/_post/rq.json", "api/test/_post/rs.json", "api/test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("testCaseId", String.valueOf(testCaseId));
        addProperty("testRunId", String.valueOf(testRunId));
        addProperty("ciTestId", UUID.randomUUID());
    }
}
