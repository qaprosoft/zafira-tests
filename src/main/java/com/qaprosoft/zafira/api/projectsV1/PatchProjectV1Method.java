package com.qaprosoft.zafira.api.projectsV1;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/projectsV1/_patch/rq.json")
@Endpoint(url = "${projects_url}/v1/projects/${key}", methodType = HttpMethodType.PATCH)
public class PatchProjectV1Method extends ZafiraBaseApiMethodWithAuth {
    public PatchProjectV1Method(String key, int leadId) {
        replaceUrlPlaceholder("projects_url", APIContextManager.PROJECT_SERVICE_URL);
        replaceUrlPlaceholder("key", key);
        setProperties("api/projectV1.properties");
        addProperty("leadId", leadId);
    }
}
