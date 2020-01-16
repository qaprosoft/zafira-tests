package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class GetBuildConsoleOutputMethod extends AbstractApiMethodV2 {
    public GetBuildConsoleOutputMethod(String accessToken, String ciRunId) {
        super("", "", "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("ciRunId", ciRunId);
        setHeaders("Authorization=Bearer " + accessToken);

    }
}
