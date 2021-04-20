package com.qaprosoft.zafira.api.projectTestRuns;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@ResponseTemplatePath(path = "api/projectTestRuns/_get/rs_search.json")
@Endpoint(url = "${api_url}/api/project-test-runs/search?projectId=${projectId}&page=1&pageSize=20", methodType = HttpMethodType.GET)
public class GetSearchProjectTestRunMethod extends ZafiraBaseApiMethodWithAuth {
    public GetSearchProjectTestRunMethod(int projectId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
    }
}
