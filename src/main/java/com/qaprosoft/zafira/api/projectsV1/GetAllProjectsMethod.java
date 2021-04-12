package com.qaprosoft.zafira.api.projectsV1;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@ResponseTemplatePath(path = "api/projectsV1/_get/rs.json")
@Endpoint(url = "${projects_url}/v1/projects?page=1&pageSize=10&sortOrder=ASC", methodType = HttpMethodType.GET)
public class GetAllProjectsMethod extends ZafiraBaseApiMethodWithAuth {
    public GetAllProjectsMethod() {
        replaceUrlPlaceholder("projects_url", APIContextManager.PROJECT_SERVICE_URL);
    }
}
