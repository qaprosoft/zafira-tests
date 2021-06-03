package com.qaprosoft.zafira.api.artifactsController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetLogsV1Method extends ZafiraBaseApiMethodWithAuth {

    public GetLogsV1Method(int testRunId , int testId) {
        super(null, "api/artifacts_controller/_get/rs_for_getLogs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
        replaceUrlPlaceholder("testId", String.valueOf(testId));
    }

}
