package com.qaprosoft.zafira.api.UserMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutUserMethod extends ZafiraBaseApiMethodWithAuth {
    public PutUserMethod(int userId, String username, String imageUrl) {
        super("api/user/_put/rq_for_update.json", "api/user/_put/rs_for_update.json", "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("id", userId);
        addProperty("username", username);
        addProperty("imageUrl", imageUrl);
    }
}
