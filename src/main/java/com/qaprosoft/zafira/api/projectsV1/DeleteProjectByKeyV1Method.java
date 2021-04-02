package com.qaprosoft.zafira.api.projectsV1;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@Endpoint(url = "${projects_url}/v1/projects/${key}", methodType = HttpMethodType.DELETE)
public class DeleteProjectByKeyV1Method extends ZafiraBaseApiMethodWithAuth {
    public DeleteProjectByKeyV1Method(String key) {
        replaceUrlPlaceholder("projects_url", APIContextManager.PROJECT_SERVICE_URL);
        replaceUrlPlaceholder("key", key);
    }
}
