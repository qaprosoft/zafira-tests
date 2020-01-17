package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteLauncherMethod extends AbstractApiMethodV2 {

    public DeleteLauncherMethod(String accessToken, int launcherId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(launcherId));
        setHeaders("Authorization=Bearer " + accessToken);
    }

}
