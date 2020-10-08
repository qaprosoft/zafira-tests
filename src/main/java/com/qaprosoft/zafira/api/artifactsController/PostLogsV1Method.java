package com.qaprosoft.zafira.api.artifactsController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class PostLogsV1Method extends ZafiraBaseApiMethodWithAuth {

    public PostLogsV1Method(int testRunId, int testId) {
        super("api/artifacts_controller/_post/rq_for_Logs.json", null, new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
        addProperty("testId", String.valueOf(testId));
    }

}
