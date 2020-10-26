package com.qaprosoft.zafira.api.user.v1;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostPasswordResetMethodV1 extends AbstractApiMethodV2 {
    public PostPasswordResetMethodV1(String email) {
        super("api/user/v1/_post/rq_for_password_reset.json", null, "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        addProperty("email", email);
    }
}
