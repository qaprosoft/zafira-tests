package com.qaprosoft.zafira.api.testSessionController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.time.OffsetDateTime;
import java.util.Properties;

public class PutSessionForLinkingV1Method extends ZafiraBaseApiMethodWithAuth {

    public PutSessionForLinkingV1Method(int id) {
        super("api/session_controller/_put/rq_for_linking.json",
                "api/session_controller/_put/rs_for_linking.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("id", String.valueOf(id));
        addProperty("endedAt", OffsetDateTime.now());
    }
}
