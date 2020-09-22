package com.qaprosoft.zafira.api.dashboard;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetAllDashboardMethod extends ZafiraBaseApiMethodWithAuth {
    public GetAllDashboardMethod() {
        super(null, "api/dashboard/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
