package com.qaprosoft.zafira.api.testRunController.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.time.OffsetDateTime;


public class PutFinishTestRunV1Method extends ZafiraBaseApiMethodWithAuth {
        OffsetDateTime date =OffsetDateTime.now();
        public PutFinishTestRunV1Method(int ciRunId) {
        super("api/test_run/v1/_put/rq.json", null, "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(ciRunId));
        OffsetDateTime date =OffsetDateTime.now();
        addProperty("endedAt", date);
        }
}
