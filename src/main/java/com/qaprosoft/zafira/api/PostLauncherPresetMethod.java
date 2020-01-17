package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostLauncherPresetMethod extends AbstractApiMethodV2 {

    public PostLauncherPresetMethod(String accessToken, int launcherId) {
        super("api/preset/_post/rq.json", "api/preset/_post/rs.json", "api/preset.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("launcherId", String.valueOf(launcherId));
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
