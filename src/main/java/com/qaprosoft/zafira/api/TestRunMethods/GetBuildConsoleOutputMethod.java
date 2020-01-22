package com.qaprosoft.zafira.api.TestRunMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class GetBuildConsoleOutputMethod extends ZafiraBaseApiMethodWithAuth {
    public GetBuildConsoleOutputMethod(String ciRunId) {
        super("", "", "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("ciRunId", ciRunId);

    }
}
