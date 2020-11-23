package com.qaprosoft.zafira.api.dashboard.widget;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutABatchOfWidgetsInDashboardMethod extends ZafiraBaseApiMethodWithAuth {
    public PutABatchOfWidgetsInDashboardMethod(int dashboardId, int id) {
        super("api/dashboard/widget/_put/rq_for_batch.json", "api/dashboard/widget/_put/rs_for_batch.json",
                "api/widget.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("dashboardId", String.valueOf(dashboardId));
        addProperty("id",String.valueOf(id));
    }
}
