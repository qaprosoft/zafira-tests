package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetBuildNumberMethod extends AbstractApiMethodV2 {

    public GetBuildNumberMethod(String accessToken, String queueItemUrl) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("queueItemUrl", queueItemUrl);
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
