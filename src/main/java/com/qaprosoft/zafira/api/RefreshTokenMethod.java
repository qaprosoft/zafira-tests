package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class RefreshTokenMethod extends AbstractApiMethodV2 {

    public RefreshTokenMethod() {
        super("api/auth/_post/rq_for_refresh.json", null, "api/auth.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }

}
