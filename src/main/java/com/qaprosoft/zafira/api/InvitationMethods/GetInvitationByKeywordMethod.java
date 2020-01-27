package com.qaprosoft.zafira.api.InvitationMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetInvitationByKeywordMethod extends ZafiraBaseApiMethodWithAuth {
    public GetInvitationByKeywordMethod(String keyword) {
        super(null, "api/invitation/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("keyword", keyword);
    }
}
