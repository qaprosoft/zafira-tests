package com.qaprosoft.zafira.api.preset;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class DeleteLauncherPresetMethod extends ZafiraBaseApiMethodWithAuth {

    public DeleteLauncherPresetMethod(int launcherId, int id) {
        super(null, null, "api/preset.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("launcherId", String.valueOf(launcherId));
        replaceUrlPlaceholder("id", String.valueOf(id));
    }
}
