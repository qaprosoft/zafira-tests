package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetLauncherWebHookMethod extends AbstractApiMethodV2 {

    public GetLauncherWebHookMethod(String accessToken, int launcherId, int presetId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("launcherId", String.valueOf(launcherId));
        replaceUrlPlaceholder("id", String.valueOf(presetId));
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
