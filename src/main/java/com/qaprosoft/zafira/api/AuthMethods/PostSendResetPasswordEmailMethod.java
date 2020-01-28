package com.qaprosoft.zafira.api.AuthMethods;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostSendResetPasswordEmailMethod extends AbstractApiMethodV2 {
    public PostSendResetPasswordEmailMethod(String email) {
        super("api/user/_post/rq_for_send_password.json", null, "api/auth.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("email", email);
    }
}
