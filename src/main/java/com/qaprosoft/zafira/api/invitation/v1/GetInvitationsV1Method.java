package com.qaprosoft.zafira.api.invitation.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetInvitationsV1Method extends ZafiraBaseApiMethodWithAuth {
    public GetInvitationsV1Method() {
        super(null, "api/invitation/v1/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
    }
}
