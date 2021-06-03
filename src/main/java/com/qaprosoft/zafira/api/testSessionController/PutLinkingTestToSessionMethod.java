package com.qaprosoft.zafira.api.testSessionController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Properties;

public class PutLinkingTestToSessionMethod extends ZafiraBaseApiMethodWithAuth {

    public PutLinkingTestToSessionMethod(int testRunId, List testIds, int sessionId) {
        super("api/session_controller/_put/rq_for_linking.json",
                "api/session_controller/_put/rs_for_linking.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
        replaceUrlPlaceholder("testSessionId", String.valueOf(sessionId));
        addProperty("testIds", testIds);
    }
}
