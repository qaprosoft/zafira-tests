package com.qaprosoft.zafira.api.testController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetWorkItemMethod extends ZafiraBaseApiMethodWithAuth {

    public GetWorkItemMethod(int testId, String workItemType) {
        super(null, "api/test/_get/rs_fot_get_workitem.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testId));
        replaceUrlPlaceholder("type", workItemType);
    }
}
