package com.qaprosoft.zafira.api.milestones;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@Endpoint(url = "${api_url}/v1/milestones/${id}?projectId=${projectId}", methodType = HttpMethodType.DELETE)
public class DeleteMilestoneByIdAndProjectIdMethod extends ZafiraBaseApiMethodWithAuth{
    public DeleteMilestoneByIdAndProjectIdMethod(int projectId, int milestoneId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(milestoneId));
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
    }

    public void setAuthHeaders() {
        String accessToken = new APIContextManager().getAccessToken();
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
