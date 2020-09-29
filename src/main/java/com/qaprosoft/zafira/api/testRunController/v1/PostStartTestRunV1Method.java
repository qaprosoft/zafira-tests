package com.qaprosoft.zafira.api.testRunController.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.time.OffsetDateTime;
import java.util.UUID;


public class PostStartTestRunV1Method extends ZafiraBaseApiMethodWithAuth {
        OffsetDateTime date =OffsetDateTime.now();
        public PostStartTestRunV1Method() {
        super("api/test_run/v1/_post/rq.json", "api/test_run/v1/_post/rs.json", "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("project", APIContextManager.PROJECT_KEY);
        addProperty("uuid", UUID.randomUUID());
        addProperty("startedAt",date);

    }
}
