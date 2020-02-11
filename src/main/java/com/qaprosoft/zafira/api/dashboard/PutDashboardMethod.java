package com.qaprosoft.zafira.api.dashboard;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutDashboardMethod extends ZafiraBaseApiMethodWithAuth {

    public PutDashboardMethod(int dashboardId, String dashboardName) {
        super("api/dashboard/_put/rq.json", "api/dashboard/_put/rs.json", "api/dashboard.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("id", dashboardId);
        addProperty("title", dashboardName);
    }
}
