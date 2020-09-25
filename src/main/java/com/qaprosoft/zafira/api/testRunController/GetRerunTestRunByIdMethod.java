package com.qaprosoft.zafira.api.testRunController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetRerunTestRunByIdMethod extends ZafiraBaseApiMethodWithAuth {
    public GetRerunTestRunByIdMethod(int testRunId, boolean rerunFailures) {
        super(null, "api/test_run/_get/rs_for_rerun.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testRunId));
        replaceUrlPlaceholder("rerunFailures", String.valueOf(rerunFailures));
    }
}
