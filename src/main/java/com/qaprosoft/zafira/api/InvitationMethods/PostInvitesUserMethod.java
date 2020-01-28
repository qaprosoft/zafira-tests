package com.qaprosoft.zafira.api.InvitationMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostInvitesUserMethod extends ZafiraBaseApiMethodWithAuth {
    public PostInvitesUserMethod(String email, String time) {
        super("api/invitation/_post/rq.json", "api/invitation/_post/rs.json", "api/invitation.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("email", email);
        addProperty("createdAt", time);
        addProperty("modifiedAt", time);
    }
}
