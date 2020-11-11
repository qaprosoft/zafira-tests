package com.qaprosoft.zafira.api.dashboard.attributes;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostABatchOfDashboardAttributesMethod extends ZafiraBaseApiMethodWithAuth {
    public PostABatchOfDashboardAttributesMethod(int dashboardId) {
        super("api/dashboard/attributes/_post/rq_for_batch.json", "api/dashboard/attributes/_post/rs.json", "api/dashboard.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("dashboardId", String.valueOf(dashboardId));
    }
}
