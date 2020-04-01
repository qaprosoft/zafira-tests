package com.qaprosoft.zafira.api.auth;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostRefreshTokenMethod extends AbstractApiMethodV2 {

    public PostRefreshTokenMethod() {
        super("api/auth/_post/rq_for_refresh.json", null, "api/auth.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
