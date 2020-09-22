package com.qaprosoft.zafira.api.user;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutUserStatusMethod extends ZafiraBaseApiMethodWithAuth {
    public PutUserStatusMethod(int userId, String status, String username) {
        super("api/user/_put/rq_for_status.json", "api/user/_put/rs_for_status.json", "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("userId", String.valueOf(userId));
        addProperty("status", status);
        addProperty("username", username);
    }
}
