package com.qaprosoft.zafira.api.projectsV1;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/projectsV1/_put/rq_for_switch.json")
@Endpoint(url = "${projects_url}/v1/project-switches", methodType = HttpMethodType.PUT)
public class PutProjectSwitchV1Method extends ZafiraBaseApiMethodWithAuth {
    public PutProjectSwitchV1Method(int projectId) {
        replaceUrlPlaceholder("projects_url", APIContextManager.PROJECT_SERVICE_URL);
        addProperty("projectId", projectId);
    }
}
