package com.qaprosoft.zafira.api.testRunController.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteTestRunByIdV1Method extends ZafiraBaseApiMethodWithAuth {

    public DeleteTestRunByIdV1Method(int testRunId) {
        super(null, null, new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("id", String.valueOf(testRunId));
    }
}
