package com.qaprosoft.zafira.api.projectDashbords;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/projectDashboards/_put/rq.json")
@ResponseTemplatePath(path = "api/projectDashboards/_put/rs.json")
@Endpoint(url = "${api_url}/api/project-dashboards", methodType = HttpMethodType.PUT)
public class PutProjectDashboard extends ZafiraBaseApiMethodWithAuth {
    public PutProjectDashboard(int projectId, int dashboardId, String title) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        addProperty("projectId", String.valueOf(projectId));
        addProperty("title", title);
        addProperty("id", dashboardId);
    }
}
