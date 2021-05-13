package com.qaprosoft.zafira.api.projectDashbords;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@Endpoint(url = "${api_url}/api/project-dashboards/${dashboardId}/widgets/${id}", methodType = HttpMethodType.DELETE)
public class DeleteWidgetFromProjectDashboard extends ZafiraBaseApiMethodWithAuth {
    public DeleteWidgetFromProjectDashboard(int dashboardId, int widgetId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("dashboardId", String.valueOf(dashboardId));
       replaceUrlPlaceholder("id", String.valueOf(widgetId));
    }
}
