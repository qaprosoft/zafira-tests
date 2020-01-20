package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetBuildNumberMethod extends ZafiraBaseApiMethodWithAuth {

    public GetBuildNumberMethod(String queueItemUrl) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("queueItemUrl", queueItemUrl);
    }
}
