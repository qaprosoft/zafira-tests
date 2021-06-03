package com.qaprosoft.zafira.api.preset;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutLauncherPresetMethod extends ZafiraBaseApiMethodWithAuth {

    public PutLauncherPresetMethod(int launcherId, int id) {
        super("api/preset/_put/rq.json", "api/preset/_put/rs.json", "api/preset.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("launcherId", String.valueOf(launcherId));
        replaceUrlPlaceholder("id", String.valueOf(id));
    }
}
