package com.qaprosoft.zafira.api.testSessionController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.time.OffsetDateTime;
import java.util.Properties;

public class PostSessionV1Method extends ZafiraBaseApiMethodWithAuth {

    public PostSessionV1Method(int id) {
        super("api/session_controller/_post/rq_for_start.json", "api/session_controller/_post/rs_for_start.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("id", String.valueOf(id));
        addProperty("startedAt", OffsetDateTime.now());
    }
}
