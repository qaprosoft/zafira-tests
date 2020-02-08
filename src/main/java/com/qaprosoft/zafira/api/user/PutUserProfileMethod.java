package com.qaprosoft.zafira.api.user;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutUserProfileMethod extends ZafiraBaseApiMethodWithAuth {
    public PutUserProfileMethod(int userId, String lastName, String username) {
        super("api/user/_put/rq_for_profile.json", "api/user/_put/rs_for_profile.json", "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("userId", userId);
        addProperty("lastName", lastName);
        addProperty("username", username);
    }
}
