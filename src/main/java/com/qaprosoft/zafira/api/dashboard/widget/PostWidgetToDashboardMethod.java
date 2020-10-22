package com.qaprosoft.zafira.api.dashboard.widget;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostWidgetToDashboardMethod extends ZafiraBaseApiMethodWithAuth {
    public PostWidgetToDashboardMethod(String widgetName,int dashboardId) {
        super("api/dashboard/widget/_post/rqNew.json", "api/dashboard/widget/_post/rs.json",
                "api/widget.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(dashboardId));
    }
}
