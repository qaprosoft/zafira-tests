package com.qaprosoft.zafira.api.invitation;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostResendInviteUserMethod extends ZafiraBaseApiMethodWithAuth {
    public PostResendInviteUserMethod(String email, String time) {
        super("api/invitation/_post/rq_resend.json", "api/invitation/_post/rs_resend.json",
                "api/invitation.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("email", email);
        addProperty("createdAt", time);
        addProperty("modifiedAt", time);
    }
}
