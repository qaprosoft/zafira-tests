package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetRerunTestRunByIdMethod extends AbstractApiMethodV2 {
    public GetRerunTestRunByIdMethod(String accessToken, int testRunId, boolean rerunFailures) {
        super(null, "api/test_run/_get/rs_for_rerun.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testRunId));
        replaceUrlPlaceholder("rerunFailures", String.valueOf(rerunFailures));
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
