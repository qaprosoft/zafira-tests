package com.qaprosoft.zafira.api.milestones;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/milestone/_patch/rq.json")
@Endpoint(url = "${api_url}/v1/milestones/${id}?projectId=${projectId}", methodType = HttpMethodType.PATCH)
public class PatchMilestoneMethod extends ZafiraBaseApiMethodWithAuth {
    public PatchMilestoneMethod(int projectId, int milestoneId, Boolean isCompleted) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(milestoneId));
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
        setProperties("api/milestone.properties");
        addProperty("value", String.valueOf(isCompleted));
    }
}
