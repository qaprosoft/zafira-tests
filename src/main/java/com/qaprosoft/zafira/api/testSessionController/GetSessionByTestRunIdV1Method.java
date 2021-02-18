package com.qaprosoft.zafira.api.testSessionController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.time.OffsetDateTime;
import java.util.Properties;
import java.util.UUID;

public class GetSessionByTestRunIdV1Method extends ZafiraBaseApiMethodWithAuth {

    public GetSessionByTestRunIdV1Method(int testRunId) {
        super(null, "api/session_controller/_get/rs_by_test_run_id.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
        addProperty("testRunId", testRunId);
    }
}
