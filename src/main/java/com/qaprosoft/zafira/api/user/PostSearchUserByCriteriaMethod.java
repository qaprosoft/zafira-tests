package com.qaprosoft.zafira.api.user;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostSearchUserByCriteriaMethod extends ZafiraBaseApiMethodWithAuth {
    public PostSearchUserByCriteriaMethod(String query) {
        super("api/user/_post/rq.json", "api/user/_post/rs.json", "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("query", query);
    }
}
