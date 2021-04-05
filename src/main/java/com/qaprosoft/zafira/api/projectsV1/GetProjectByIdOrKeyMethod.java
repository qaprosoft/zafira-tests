package com.qaprosoft.zafira.api.projectsV1;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.SuccessfulHttpStatus;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.carina.core.foundation.api.http.HttpResponseStatusType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@ResponseTemplatePath(path = "api/projectsV1/_get/rs_by_id_or_key.json")
@Endpoint(url = "${projects_url}/v1/projects/${idOrKey}", methodType = HttpMethodType.GET)
public class GetProjectByIdOrKeyMethod extends ZafiraBaseApiMethodWithAuth {
    public GetProjectByIdOrKeyMethod(Object idOrKey) {
        replaceUrlPlaceholder("projects_url", APIContextManager.PROJECT_SERVICE_URL);
        replaceUrlPlaceholder("idOrKey", String.valueOf(idOrKey));
    }
}
