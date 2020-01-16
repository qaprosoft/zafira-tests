package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetTestRunJobParametersMethod extends AbstractApiMethodV2 {
    public GetTestRunJobParametersMethod(String accessToken, int testRunId) {
        super(null, "api/test_run/_get/rs_for_get_params.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testRunId));
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
