package com.qaprosoft.zafira.api.invitation.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostInviteUserV1Method extends ZafiraBaseApiMethodWithAuth {
    public PostInviteUserV1Method(String email) {
        super("api/invitation/v1/_post/rq.json", "api/invitation/v1/_post/rs.json", "api/invitation.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        addProperty("email", email);
    }
}
