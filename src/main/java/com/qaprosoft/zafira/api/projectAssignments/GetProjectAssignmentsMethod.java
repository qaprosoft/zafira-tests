package com.qaprosoft.zafira.api.projectAssignments;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@ResponseTemplatePath(path = "api/projectV1Assignments/_get/rs.json")
@Endpoint(url = "${projects_url}/v1/projects/${projectId}/assignments", methodType = HttpMethodType.GET)
public class GetProjectAssignmentsMethod extends ZafiraBaseApiMethodWithAuth {
    public GetProjectAssignmentsMethod(int projectId){
        replaceUrlPlaceholder("projects_url", APIContextManager.PROJECT_SERVICE_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
    }
}
