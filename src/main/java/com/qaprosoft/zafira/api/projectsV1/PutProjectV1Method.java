package com.qaprosoft.zafira.api.projectsV1;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/projectsV1/_put/rq.json")
@ResponseTemplatePath(path = "api/projectsV1/_put/rs.json")
@Endpoint(url = "${projects_url}/v1/projects/${key}", methodType = HttpMethodType.PUT)
public class PutProjectV1Method extends ZafiraBaseApiMethodWithAuth {
    public PutProjectV1Method(String key, String name, String newKey) {
        replaceUrlPlaceholder("projects_url", APIContextManager.PROJECT_SERVICE_URL);
        replaceUrlPlaceholder("key", key);
        setProperties("api/projectV1.properties");
        addProperty("name", name);
        addProperty("key", newKey);
    }
}
