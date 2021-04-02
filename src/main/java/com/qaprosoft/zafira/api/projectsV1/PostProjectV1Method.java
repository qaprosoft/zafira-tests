package com.qaprosoft.zafira.api.projectsV1;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/projectsV1/_post/rq.json")
@ResponseTemplatePath(path = "api/projectsV1/_post/rs.json")
@Endpoint(url = "${projects_url}/v1/projects", methodType = HttpMethodType.POST)
public class PostProjectV1Method extends ZafiraBaseApiMethodWithAuth {
    public PostProjectV1Method() {
        replaceUrlPlaceholder("projects_url", APIContextManager.PROJECT_SERVICE_URL);
        setProperties("api/projectV1.properties");
    }
}
