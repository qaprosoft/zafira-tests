package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetTestRunMethod extends AbstractApiMethodV2 {

    public GetTestRunMethod(String accessToken, int testRunId) {
        super(null, "api/test_run/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("Authorization=Bearer " + accessToken);
        replaceUrlPlaceholder("id", String.valueOf(testRunId));
    }
}
