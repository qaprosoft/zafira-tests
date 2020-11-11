package com.qaprosoft.zafira.api.dashboard.attributes;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class UpdateDashboardAttributeMethod extends ZafiraBaseApiMethodWithAuth {
    public UpdateDashboardAttributeMethod(int dashboardId, int id, String key, String value) {
        super("api/dashboard/attributes/_put/rq.json", "api/dashboard/attributes/_put/rs.json", "api/dashboard.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("dashboardId", String.valueOf(dashboardId));
        addProperty("id", String.valueOf(id));
        addProperty("value", value);
        addProperty("key", key);
        addProperty("dashboardId", String.valueOf(dashboardId));
    }
}
