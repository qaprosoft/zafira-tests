package com.qaprosoft.zafira.api.group;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostGroupMethod extends ZafiraBaseApiMethodWithAuth {
    public PostGroupMethod(String groupName) {
        super("api/group/_post/rq.json", "api/group/_post/rs.json", "api/group.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("name", groupName);
    }
}
