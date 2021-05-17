package com.qaprosoft.zafira.api.projectTestRuns;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@Endpoint(url = "${api_url}/api/project-test-runs/${id}/milestone/${milestoneId}?projectId=${projectId}", methodType = HttpMethodType.DELETE)
public class DeleteTestRunFromMilestoneMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteTestRunFromMilestoneMethod(int testRunId, int milestoneId, int projectId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testRunId));
        replaceUrlPlaceholder("milestoneId", String.valueOf(milestoneId));
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
    }

    protected void setAuthHeaders() {
        String accessToken = new APIContextManager().getAccessToken();
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
