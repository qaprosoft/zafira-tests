package com.qaprosoft.zafira.api.testSessionController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetSessionByTestRunIdAndTestIdV1Method extends ZafiraBaseApiMethodWithAuth {

    public GetSessionByTestRunIdAndTestIdV1Method(int testRunId, int testId) {
        super(null, "api/session_controller/_get/rs_by_test_run_id.json",
                "api/test_session.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
        replaceUrlPlaceholder("testId", String.valueOf(testId));
        addProperty("testRunId", testRunId);
        addProperty("testId", testId);
    }
}
