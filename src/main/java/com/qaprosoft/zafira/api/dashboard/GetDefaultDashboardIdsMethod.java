package com.qaprosoft.zafira.api.dashboard;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetDefaultDashboardIdsMethod extends ZafiraBaseApiMethodWithAuth {
    public GetDefaultDashboardIdsMethod() {
        super(null, "api/dashboard/_get/rs_default_dashboards.json", new  Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
