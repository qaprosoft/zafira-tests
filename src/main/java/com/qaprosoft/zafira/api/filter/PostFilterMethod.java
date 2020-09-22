package com.qaprosoft.zafira.api.filter;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostFilterMethod extends ZafiraBaseApiMethodWithAuth {
    public PostFilterMethod(String filterName) {
        super("api/filter/_post/rq.json", "api/filter/_post/rs.json", "api/filter.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("name", filterName);
    }
}
