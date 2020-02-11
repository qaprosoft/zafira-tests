package com.qaprosoft.zafira.api.dashboard;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutDashboardOrderMethod extends ZafiraBaseApiMethodWithAuth {
    public PutDashboardOrderMethod(int dashboardId, int positionNumber) {
        super("api/dashboard/_put/rq_for_order.json", null, "api/dashboard.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("id", dashboardId);
        addProperty("position", positionNumber);
    }
}
