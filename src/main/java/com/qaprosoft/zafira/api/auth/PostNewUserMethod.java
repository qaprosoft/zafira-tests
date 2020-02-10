package com.qaprosoft.zafira.api.auth;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostNewUserMethod extends AbstractApiMethodV2 {
    public PostNewUserMethod(String token, String username, String email) {
        super("api/auth/_post/rq_for_user.json", null, "api/auth.properties");
        setHeaders("Access-Token= " + token);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("username", username);
        addProperty("email", email);
    }
}
