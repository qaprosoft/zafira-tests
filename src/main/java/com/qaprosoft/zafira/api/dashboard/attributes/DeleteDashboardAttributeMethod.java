package com.qaprosoft.zafira.api.dashboard.attributes;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class DeleteDashboardAttributeMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteDashboardAttributeMethod(int dashboardId, int id) {
        super(null, null, "api/dashboard.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("dashboardId", String.valueOf(dashboardId));
        replaceUrlPlaceholder("id", String.valueOf(id));
    }
}
