package com.qaprosoft.zafira.api.testRunController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetTestRunByCiRunIdMethod extends ZafiraBaseApiMethodWithAuth {

    public GetTestRunByCiRunIdMethod(String ciRunId) {
        super(null, "api/test_run/_get/rs_by_siRunId.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("ciRunId", ciRunId);
    }
}
