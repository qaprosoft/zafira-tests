package com.qaprosoft.zafira.api.dashboard.attributes;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostDashboardAttributeMethod extends ZafiraBaseApiMethodWithAuth {
    public PostDashboardAttributeMethod(int dashboardId, String key, String value) {
        super("api/dashboard/attributes/_post/rq.json", "api/dashboard/attributes/_post/rs.json", "api/dashboard.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("dashboardId", String.valueOf(dashboardId));
        addProperty("value", value);
        addProperty("key", key);
    }
}
