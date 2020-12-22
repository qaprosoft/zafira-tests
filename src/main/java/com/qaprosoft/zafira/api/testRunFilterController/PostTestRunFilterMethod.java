package com.qaprosoft.zafira.api.testRunFilterController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostTestRunFilterMethod extends ZafiraBaseApiMethodWithAuth {
    public PostTestRunFilterMethod(String filterName) {
        super("api/testRunFilter/_post/rq.json", "api/testRunFilter/_post/rs.json", "api/filter.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("name", filterName);
    }
}
