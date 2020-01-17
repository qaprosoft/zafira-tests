package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostLauncherMethod extends AbstractApiMethodV2 {
    public PostLauncherMethod(String accessToken, int jobId, int scmAccountId) {
        super("api/launcher/_post/rq.json", "api/launcher/_post/rs.json", "api/launcher.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("Authorization=Bearer " + accessToken);
        addProperty("id", String.valueOf(scmAccountId));
        addProperty("jobId", String.valueOf(jobId));
    }
}
