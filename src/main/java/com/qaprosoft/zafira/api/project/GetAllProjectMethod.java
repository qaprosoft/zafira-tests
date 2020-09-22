package com.qaprosoft.zafira.api.project;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetAllProjectMethod extends ZafiraBaseApiMethodWithAuth {
    public GetAllProjectMethod() {
        super(null, "api/project/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
