package com.qaprosoft.zafira.api.preset;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetLauncherWebHookMethod extends ZafiraBaseApiMethodWithAuth {

    public GetLauncherWebHookMethod(int launcherId, int presetId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("launcherId", String.valueOf(launcherId));
        replaceUrlPlaceholder("id", String.valueOf(presetId));
    }
}
