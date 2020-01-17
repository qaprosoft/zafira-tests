package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostLauncherFromJenkinsMethod extends AbstractApiMethodV2 {

    public PostLauncherFromJenkinsMethod(String accessToken) {
        super("api/launcher/_post/rq_for_launcher_by_jenkins.json",
                "api/launcher/_post/rs_for_launcher_by_jenkins.json", "api/launcher.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
