package com.qaprosoft.zafira.api.projectDashbords;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@ResponseTemplatePath(path = "api/projectDashboards/_get/rs_for_search.json")
@Endpoint(url = "${api_url}/api/project-dashboards/search?projectId=${projectId}&page=1&pageSize=1000&sortOrder=ASC", methodType = HttpMethodType.GET)
public class GetSearchProjectDashboards extends ZafiraBaseApiMethodWithAuth {
    public GetSearchProjectDashboards(int projectId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
    }
}
