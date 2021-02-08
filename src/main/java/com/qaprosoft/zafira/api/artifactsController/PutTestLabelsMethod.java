package com.qaprosoft.zafira.api.artifactsController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutTestLabelsMethod extends ZafiraBaseApiMethodWithAuth {
    public PutTestLabelsMethod(int testRunId, int testId) {
        super("api/artifacts_controller/labels._put/rq_for_test.json",
                null, "api/artifact.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
        replaceUrlPlaceholder("testId", String.valueOf(testId));
    }
}

