package com.qaprosoft.zafira.api.user.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import javax.management.Query;

public class GetUserByCriteriaV1Method extends ZafiraBaseApiMethodWithAuth {
    public GetUserByCriteriaV1Method(String query,String status) {
        super(null, "api/user/_post/rs.json", "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("query", query);
        replaceUrlPlaceholder("status", status);
    }
}
