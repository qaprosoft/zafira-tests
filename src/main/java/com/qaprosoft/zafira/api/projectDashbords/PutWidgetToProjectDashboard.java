package com.qaprosoft.zafira.api.projectDashbords;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/projectDashboards/widget/_put/rq.json")
@ResponseTemplatePath(path = "api/projectDashboards/widget/_put/rs.json")
@Endpoint(url = "${api_url}/api/project-dashboards/${dashboardId}/widgets/all", methodType = HttpMethodType.PUT)
public class PutWidgetToProjectDashboard extends ZafiraBaseApiMethodWithAuth {
    public PutWidgetToProjectDashboard(int dashboardId, int widgetId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("dashboardId", String.valueOf(dashboardId));
         addProperty("id", String.valueOf(widgetId));
    }
}
