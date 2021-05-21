package com.qaprosoft.zafira.api.projectIntegrations.BrowserStackController;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@Endpoint(url = "${api_url}/v1/integrations/browser-stack?projectId=${projectId}", methodType = HttpMethodType.DELETE)
public class DeleteBrowserStackIntegrationMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteBrowserStackIntegrationMethod(int projectId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
    }
}
