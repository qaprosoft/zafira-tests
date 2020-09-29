package com.qaprosoft.zafira.api.testRunController.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetTestsByCiRunIdV1Method extends ZafiraBaseApiMethodWithAuth {

    public GetTestsByCiRunIdV1Method(String ciRunId) {
        super(null, "api/test_run/_get/rs_for_getTest.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("ciRunId", String.valueOf(ciRunId));
    }
}
