package com.qaprosoft.zafira.api.authIAM;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.List;


public class PostVerifyPermissionsMethodIAM extends AbstractApiMethodV2 {
    public PostVerifyPermissionsMethodIAM(String authToken, String permissionsList) {
        super("api/authIAM/_post/rq_for_verify.json", "api/authIAM/_post/rs_for_verify.json", "api/auth.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        addProperty("authToken", authToken);
        addProperty("permissions", permissionsList);
    }
}
