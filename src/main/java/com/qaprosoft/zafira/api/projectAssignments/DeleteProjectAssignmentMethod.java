package com.qaprosoft.zafira.api.projectAssignments;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@Endpoint(url = "${projects_url}/v1/projects/${projectId}/assignments?userId=${userId}", methodType = HttpMethodType.DELETE)
public class DeleteProjectAssignmentMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteProjectAssignmentMethod(int projectId, int userId) {
        replaceUrlPlaceholder("projects_url", APIContextManager.PROJECT_SERVICE_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
        replaceUrlPlaceholder("userId", String.valueOf(userId));
    }
}
