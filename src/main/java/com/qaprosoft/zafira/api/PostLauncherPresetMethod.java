package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.manager.APIContextManager;

public class PostLauncherPresetMethod extends ZafiraBaseApiMethodWithAuth {

    public PostLauncherPresetMethod(int launcherId) {
        super("api/preset/_post/rq.json", "api/preset/_post/rs.json", "api/preset.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("launcherId", String.valueOf(launcherId));
    }
}
