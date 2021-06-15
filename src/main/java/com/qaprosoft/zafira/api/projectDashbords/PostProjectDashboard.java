package com.qaprosoft.zafira.api.projectDashbords;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/projectDashboards/_post/rq.json")
@ResponseTemplatePath(path = "api/projectDashboards/_post/rs.json")
@Endpoint(url = "${api_url}/api/project-dashboards", methodType = HttpMethodType.POST)
public class PostProjectDashboard extends ZafiraBaseApiMethodWithAuth {
    public PostProjectDashboard(int projectId, String title) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        addProperty("projectId", String.valueOf(projectId));
        addProperty("title", title);
        addProperty("hidden", String.valueOf(false));
    }
}
