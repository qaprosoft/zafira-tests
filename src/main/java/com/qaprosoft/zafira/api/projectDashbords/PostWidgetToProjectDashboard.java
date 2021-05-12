package com.qaprosoft.zafira.api.projectDashbords;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/projectDashboards/widget/_post/rq.json")
@ResponseTemplatePath(path = "api/projectDashboards/widget/_post/rs.json")
@Endpoint(url = "${api_url}/api/project-dashboards/${dashboardId}/widgets", methodType = HttpMethodType.POST)
public class PostWidgetToProjectDashboard extends ZafiraBaseApiMethodWithAuth {
    public PostWidgetToProjectDashboard(int dashboardId, int widgetId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("dashboardId", String.valueOf(dashboardId));
        addProperty("id", String.valueOf(widgetId));
    }
}
