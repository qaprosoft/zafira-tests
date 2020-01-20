package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.manager.APIContextManager;

public class PutLauncherMethod extends ZafiraBaseApiMethodWithAuth {

    public PutLauncherMethod(int launcherId, String valueToUpdate) {
        super("api/launcher/_put/rq.json", "api/launcher/_put/rs.json", "api/launcher.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("id", launcherId);
        addProperty("type", valueToUpdate);
    }
}
