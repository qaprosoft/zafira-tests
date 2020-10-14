package com.qaprosoft.zafira.api.invitation.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetInvitationByTokenV1Method extends ZafiraBaseApiMethodWithAuth {
    public GetInvitationByTokenV1Method(String token) {
        super(null, "api/invitation/v1/_get/rs_by_token.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("token", token);
    }
}
