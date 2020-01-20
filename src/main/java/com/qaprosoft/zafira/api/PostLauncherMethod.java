package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.manager.APIContextManager;

public class PostLauncherMethod extends ZafiraBaseApiMethodWithAuth {
    public PostLauncherMethod(int jobId, int scmAccountId) {
        super("api/launcher/_post/rq.json", "api/launcher/_post/rs.json", "api/launcher.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("id", String.valueOf(scmAccountId));
        addProperty("jobId", String.valueOf(jobId));
    }
}
