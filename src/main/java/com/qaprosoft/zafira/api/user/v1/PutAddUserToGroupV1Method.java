package com.qaprosoft.zafira.api.user.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class PutAddUserToGroupV1Method extends ZafiraBaseApiMethodWithAuth {
    public PutAddUserToGroupV1Method(int groupId, int userId) {
        super("api/user/_put/rq_add_to_group.json", null,
                "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("groupId", String.valueOf(groupId));
        replaceUrlPlaceholder("id", String.valueOf(userId));
        addProperty("id", String.valueOf(userId));
    }
}
