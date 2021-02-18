package com.qaprosoft.zafira.api.testSessionController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class PostSessionV1Method extends ZafiraBaseApiMethodWithAuth {

    public PostSessionV1Method(int testRunId, List testIds) {
        super("api/session_controller/_post/rq_for_start.json", "api/session_controller/_post/rs_for_start.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
        addProperty("startedAt", OffsetDateTime.now());
        addProperty("testId", testIds);
        addProperty("sessionId", UUID.randomUUID());
    }
}
