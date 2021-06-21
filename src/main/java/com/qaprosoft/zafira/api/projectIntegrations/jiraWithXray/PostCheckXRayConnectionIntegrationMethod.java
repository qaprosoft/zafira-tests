package com.qaprosoft.zafira.api.projectIntegrations.jiraWithXray;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/projectIntegrations/jira/_post/rq_for_xray.json")
@ResponseTemplatePath(path = "api/projectIntegrations/jira/_post/rs.json")
@Endpoint(url = "${api_url}/v1/integrations/jira/xray-connectivity-checks?projectId=${projectId}", methodType = HttpMethodType.POST)
public class PostCheckXRayConnectionIntegrationMethod extends ZafiraBaseApiMethodWithAuth {
    public PostCheckXRayConnectionIntegrationMethod(int projectId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));

        addProperty("xrayClientId", "xrayClientId");
        addProperty("xrayClientSecret","xrayClientSecret");
        addProperty("xrayHost", "https://xray.cloud.xpand-it.com");
        addProperty("reachable", String.valueOf(false));
    }
}
