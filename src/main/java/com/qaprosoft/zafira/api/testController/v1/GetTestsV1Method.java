package com.qaprosoft.zafira.api.testController.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetTestsV1Method extends ZafiraBaseApiMethodWithAuth {

    public GetTestsV1Method(int testRunId) {
        super(null, "api/test/v1/_get/rs.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
    }
}
