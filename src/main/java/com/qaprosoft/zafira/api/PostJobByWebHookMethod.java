package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostJobByWebHookMethod extends AbstractApiMethodV2 {

    public PostJobByWebHookMethod(String accessToken, int launcherId, String ref) {
        super("api/launcher/_post/rq_for_webHook.json", null, "api/job.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(launcherId));
        replaceUrlPlaceholder("ref", ref);
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
