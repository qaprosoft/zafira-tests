package com.qaprosoft.zafira.api.TestRunMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetTestRunJobParametersMethod extends ZafiraBaseApiMethodWithAuth {
    public GetTestRunJobParametersMethod(int testRunId) {
        super(null, "api/test_run/_get/rs_for_get_params.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testRunId));
    }
}
