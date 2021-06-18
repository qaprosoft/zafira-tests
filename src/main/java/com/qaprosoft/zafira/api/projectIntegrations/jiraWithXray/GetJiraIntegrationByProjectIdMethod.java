package com.qaprosoft.zafira.api.projectIntegrations.jiraWithXray;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.io.UnsupportedEncodingException;

@ResponseTemplatePath(path = "api/projectIntegrations/jira/_get/rs.json")
@Endpoint(url = "${api_url}/v1/integrations/jira?projectId=${projectId}",
        methodType = HttpMethodType.GET)
public class GetJiraIntegrationByProjectIdMethod extends ZafiraBaseApiMethodWithAuth {
    public GetJiraIntegrationByProjectIdMethod(int projectId) throws UnsupportedEncodingException {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
    }
}
