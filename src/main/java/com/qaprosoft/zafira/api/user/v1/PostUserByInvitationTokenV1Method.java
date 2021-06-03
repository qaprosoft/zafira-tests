package com.qaprosoft.zafira.api.user.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;
import org.apache.commons.lang3.RandomStringUtils;

public class PostUserByInvitationTokenV1Method extends ZafiraBaseApiMethodWithAuth {
    public PostUserByInvitationTokenV1Method(String username, String password, String invitationToken, String email) {
        super("api/user/v1/_post/rq.json", "api/user/v1/_post/rs.json", "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("invitation-token", invitationToken);
        addProperty("username", username);
        addProperty("password", password);
        addProperty("email", email );
        setHeaders("x-zbr-tenant=automation");
    }
}
