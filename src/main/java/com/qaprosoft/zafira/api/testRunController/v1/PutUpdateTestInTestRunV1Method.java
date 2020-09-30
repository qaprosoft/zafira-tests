package com.qaprosoft.zafira.api.testRunController.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.time.OffsetDateTime;


public class PutUpdateTestInTestRunV1Method extends ZafiraBaseApiMethodWithAuth {
    public PutUpdateTestInTestRunV1Method(int testRunId, int testId, String result) {
        super("api/test_run/v1/_put/rq_for_update.json", "api/test_run/v1/_put/rs_for_update.json",
                "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
        replaceUrlPlaceholder("testId", String.valueOf(testId));
        addProperty("endedAt", OffsetDateTime.now());
        addProperty("result", result);
    }
}
