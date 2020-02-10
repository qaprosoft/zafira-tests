package com.qaprosoft.zafira.api.dashboard;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostDashboardMethod extends ZafiraBaseApiMethodWithAuth {
    public PostDashboardMethod(String dashboardName) {
        super("api/dashboard/_post/rq.json", "api/dashboard/_post/rs.json", "api/dasnboard.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("title", dashboardName);
    }
}
