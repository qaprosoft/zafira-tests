package com.qaprosoft.zafira.api.milestones;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@ResponseTemplatePath(path = "api/milestone/_get/rs.json")
@Endpoint(url = "${api_url}/v1/milestones", methodType = HttpMethodType.GET)
public class GetMilestonesByProjectIdMethod extends ZafiraBaseApiMethodWithAuth {
    public GetMilestonesByProjectIdMethod(int projectId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        setProperties("api/milestone.properties");
        addProperty("projectId", projectId);
        addUrlParameter("projectId", String.valueOf(projectId));
    }
}
