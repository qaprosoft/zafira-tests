package com.qaprosoft.zafira.api.dashboard;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetDashboardByIdMethod extends ZafiraBaseApiMethodWithAuth {
    public GetDashboardByIdMethod(int dashboardId) {
        super(null, "api/dashboard/_get/rs_by_id.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(dashboardId));
    }
}
