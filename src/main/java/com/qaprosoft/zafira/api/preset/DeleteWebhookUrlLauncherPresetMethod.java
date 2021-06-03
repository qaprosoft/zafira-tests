package com.qaprosoft.zafira.api.preset;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class DeleteWebhookUrlLauncherPresetMethod extends ZafiraBaseApiMethodWithAuth {

    public DeleteWebhookUrlLauncherPresetMethod(int launcherId, int id, String webhook) {
        super(null, null, "api/preset.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("launcherId", String.valueOf(launcherId));
        replaceUrlPlaceholder("id", String.valueOf(id));
        replaceUrlPlaceholder("webhook", String.valueOf(webhook));
    }
}
