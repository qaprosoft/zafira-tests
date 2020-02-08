package com.qaprosoft.zafira.api.launcher;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostJobByWebHookMethod extends ZafiraBaseApiMethodWithAuth {

    public PostJobByWebHookMethod(int launcherId, String ref) {
        super("api/launcher/_post/rq_for_webHook.json", null, "api/job.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(launcherId));
        replaceUrlPlaceholder("ref", ref);
    }
}
