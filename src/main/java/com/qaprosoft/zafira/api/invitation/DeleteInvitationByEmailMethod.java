package com.qaprosoft.zafira.api.invitation;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteInvitationByEmailMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteInvitationByEmailMethod(String email) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("email", email);
    }
}
