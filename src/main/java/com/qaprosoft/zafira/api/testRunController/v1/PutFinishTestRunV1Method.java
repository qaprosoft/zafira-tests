package com.qaprosoft.zafira.api.testRunController.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.time.OffsetDateTime;


public class PutFinishTestRunV1Method extends ZafiraBaseApiMethodWithAuth {
        public PutFinishTestRunV1Method(int testRunId) {
        super("api/test_run/v1/_put/rq_for_finish.json", null, "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
        addProperty("endedAt", OffsetDateTime.now());
        }
}
