package com.qaprosoft.zafira.api.user;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutAddUserToGroupMethod extends ZafiraBaseApiMethodWithAuth {
    public PutAddUserToGroupMethod(int groupId, int userId) {
        super("api/user/_put/rq_add_to_group.json", "api/user/_put/rs_add_to_group.json",
                "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("groupId", String.valueOf(groupId));
        addProperty("id", String.valueOf(userId));
    }
}
