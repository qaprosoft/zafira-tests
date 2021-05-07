package com.qaprosoft.zafira.api.projectDashbords;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@ResponseTemplatePath(path = "api/projectDashboards/_get/rs_by_id.json")
@Endpoint(url = "${api_url}/api/project-dashboards/${id}", methodType = HttpMethodType.GET)
public class GetProjectDashboardById extends ZafiraBaseApiMethodWithAuth {
    public GetProjectDashboardById(int dashboardId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(dashboardId));
    }
}
