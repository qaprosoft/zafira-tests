package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetAllGroupsMethod extends ZafiraBaseApiMethodWithAuth {
    public GetAllGroupsMethod() {
        super(null, "api/group/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
