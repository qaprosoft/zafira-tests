package com.qaprosoft.zafira.api.testSessionController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

public class PutSessionV1Method extends ZafiraBaseApiMethodWithAuth {

    public PutSessionV1Method(int id, List testId,  int sessionId) {
        super("api/session_controller/_put/rq_for_finish.json", "api/session_controller/_put/rs_for_finish.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(id));
        replaceUrlPlaceholder("testSessionId", String.valueOf(sessionId));
        addProperty("endedAt", OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
        addProperty("testId", testId);
    }
}
