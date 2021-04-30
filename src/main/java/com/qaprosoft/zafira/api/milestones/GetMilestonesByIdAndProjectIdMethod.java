package com.qaprosoft.zafira.api.milestones;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@ResponseTemplatePath(path = "api/milestone/_get/rs_by_id.json")
@Endpoint(url = "${api_url}/v1/milestones/${id}", methodType = HttpMethodType.GET)
public class GetMilestonesByIdAndProjectIdMethod extends ZafiraBaseApiMethodWithAuth {
    public GetMilestonesByIdAndProjectIdMethod(int projectId,int milestoneId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(milestoneId));
        setProperties("api/milestone.properties");
        addProperty("projectId", projectId);
        addUrlParameter("projectId", String.valueOf(projectId));
    }
}
