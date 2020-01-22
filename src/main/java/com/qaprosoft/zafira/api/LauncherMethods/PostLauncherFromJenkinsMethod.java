package com.qaprosoft.zafira.api.LauncherMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostLauncherFromJenkinsMethod extends ZafiraBaseApiMethodWithAuth {

    public PostLauncherFromJenkinsMethod() {
        super("api/launcher/_post/rq_for_launcher_by_jenkins.json",
                "api/launcher/_post/rs_for_launcher_by_jenkins.json", "api/launcher.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
