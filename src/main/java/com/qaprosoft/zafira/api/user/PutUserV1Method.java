package com.qaprosoft.zafira.api.user;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutUserV1Method extends ZafiraBaseApiMethodWithAuth {
    public PutUserV1Method(String userId, String username, String imageUrl) {
        super("api/user/_put/rq_for_update.json", "api/user/_put/rs_for_update.json", "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("userId", String.valueOf(userId));
        addProperty("id", String.valueOf(userId));
        addProperty("username", username);
        addProperty("imageUrl", imageUrl);
    }
}
