package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutLauncherMethod extends AbstractApiMethodV2 {

    public PutLauncherMethod(String accessToken, int launcherId, String valueToUpdate) {
        super("api/launcher/_put/rq.json", "api/launcher/_put/rs.json", "api/launcher.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("Authorization=Bearer " + accessToken);
        addProperty("id", launcherId);
        addProperty("type", valueToUpdate);
    }
}
