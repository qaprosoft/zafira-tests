package com.qaprosoft.zafira.api.projectIntegrations.jiraWithXray;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/projectIntegrations/jira/_patch/rq.json")
@Endpoint(url = "${api_url}/v1/integrations/jira?projectId=${projectId}", methodType = HttpMethodType.PATCH)
public class PatchEnabledJiraIntegrationMethod extends ZafiraBaseApiMethodWithAuth {
    public PatchEnabledJiraIntegrationMethod(int projectId, Boolean enabledValue) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));

        addProperty("value", String.valueOf(enabledValue));
    }
}
