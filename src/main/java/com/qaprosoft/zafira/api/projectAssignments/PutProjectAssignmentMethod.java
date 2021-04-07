package com.qaprosoft.zafira.api.projectAssignments;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@ResponseTemplatePath(path = "api/projectV1Assignments/_put/rs.json")
@RequestTemplatePath(path = "api/projectV1Assignments/_put/rq.json")
@Endpoint(url = "${projects_url}/v1/projects/${projectId}/assignments", methodType = HttpMethodType.PUT)
public class PutProjectAssignmentMethod extends ZafiraBaseApiMethodWithAuth {
    public PutProjectAssignmentMethod(int projectId, int userId, String role){
        replaceUrlPlaceholder("projects_url", APIContextManager.PROJECT_SERVICE_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
        addProperty("userId", userId);
        addProperty("role", role);
    }
}
