package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetDebugJobMethod extends AbstractApiMethodV2 {
    public GetDebugJobMethod(String accessToken, int testRunId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testRunId));
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
