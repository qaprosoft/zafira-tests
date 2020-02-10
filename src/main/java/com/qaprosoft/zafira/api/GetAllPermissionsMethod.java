package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetAllPermissionsMethod extends ZafiraBaseApiMethodWithAuth {
    public GetAllPermissionsMethod() {
        super(null, "api/permission/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
