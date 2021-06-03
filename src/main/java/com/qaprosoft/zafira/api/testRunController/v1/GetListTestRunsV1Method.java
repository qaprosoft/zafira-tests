package com.qaprosoft.zafira.api.testRunController.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetListTestRunsV1Method extends ZafiraBaseApiMethodWithAuth {

    public GetListTestRunsV1Method() {
        super(null, "api/test_run/v1/_get/rs_for_list.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
    }
}
