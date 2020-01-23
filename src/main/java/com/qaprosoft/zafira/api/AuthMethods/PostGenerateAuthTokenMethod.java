package com.qaprosoft.zafira.api.AuthMethods;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostGenerateAuthTokenMethod extends AbstractApiMethodV2 {
    public PostGenerateAuthTokenMethod(String username, String password) {
        super("api/auth/_post/rq_for_generate.json", "api/auth/_post/rs_for_generate.json",
                "api/auth.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("password", password);
        addProperty("username", username);
    }
}
