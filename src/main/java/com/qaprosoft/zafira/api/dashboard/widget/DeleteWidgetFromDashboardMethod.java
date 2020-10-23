package com.qaprosoft.zafira.api.dashboard.widget;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class DeleteWidgetFromDashboardMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteWidgetFromDashboardMethod(int dashboardId, int widgetId) {
        super(null, null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("dashboardId", String.valueOf(dashboardId));
        replaceUrlPlaceholder("widgetId", String.valueOf(widgetId));
    }
}
