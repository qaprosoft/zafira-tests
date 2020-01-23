package com.qaprosoft.zafira.api.AuthMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetApiTokenMethod extends ZafiraBaseApiMethodWithAuth {
    public GetApiTokenMethod() {
        super(null, "api/auth/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
