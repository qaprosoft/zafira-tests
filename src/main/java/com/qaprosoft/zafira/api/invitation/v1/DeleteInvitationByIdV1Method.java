package com.qaprosoft.zafira.api.invitation.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteInvitationByIdV1Method extends ZafiraBaseApiMethodWithAuth {
    public DeleteInvitationByIdV1Method(int id) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("id", String.valueOf(id));
    }
}
