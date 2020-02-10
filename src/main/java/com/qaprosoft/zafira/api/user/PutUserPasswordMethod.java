package com.qaprosoft.zafira.api.user;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutUserPasswordMethod extends ZafiraBaseApiMethodWithAuth {
    public PutUserPasswordMethod(int userId) {
        super("api/user/_put/rq_for_password.json", null, "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("id", userId);
    }
}
