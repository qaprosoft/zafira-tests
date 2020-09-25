package com.qaprosoft.zafira.api.authIAM;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;


public class PostVerifyPermissionsMethodIAM extends AbstractApiMethodV2 {
    public PostVerifyPermissionsMethodIAM() {
        super("api/authIAM/_post/rq_for_verify.json", "api/authIAM/_post/rs_for_verify.json", "api/auth.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        addProperty("authToken",new APIContextManager().getAccessToken());
    }
}
