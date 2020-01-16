package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteTestRunMethod extends AbstractApiMethodV2 {

    public DeleteTestRunMethod(String accessToken, int testRunId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("Authorization=Bearer " + accessToken);
        replaceUrlPlaceholder("id", String.valueOf(testRunId));
    }
}
