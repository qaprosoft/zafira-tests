package com.qaprosoft.zafira.api.projectIntegrations.jiraWithXray;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@ResponseTemplatePath(path = "api/projectIntegrations/jira/_get/rs_for_issues.json")
@Endpoint(url = "${api_url}/v1/integrations/jira/issues/${issueId}?projectId=${projectId}",
        methodType = HttpMethodType.GET)
public class GetJiraIssueByProjectIdMethod extends ZafiraBaseApiMethodWithAuth {
    public GetJiraIssueByProjectIdMethod(int projectId, String issueId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
        replaceUrlPlaceholder("issueId", issueId);
    }
}
