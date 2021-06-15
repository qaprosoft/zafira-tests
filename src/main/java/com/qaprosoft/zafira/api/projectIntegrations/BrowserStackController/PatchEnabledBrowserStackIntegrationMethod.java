package com.qaprosoft.zafira.api.projectIntegrations.BrowserStackController;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/projectIntegrations/browserStack/_patch/rq.json")
@Endpoint(url = "${api_url}/v1/integrations/browser-stack?projectId=${projectId}", methodType = HttpMethodType.PATCH)
public class PatchEnabledBrowserStackIntegrationMethod extends ZafiraBaseApiMethodWithAuth {
    public PatchEnabledBrowserStackIntegrationMethod(int projectId, Boolean enabledValue) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));

        addProperty("value", String.valueOf(enabledValue));
    }

    public PatchEnabledBrowserStackIntegrationMethod(int projectId, Boolean enabledValue,String token) {
        super(token);
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));

        addProperty("value", String.valueOf(enabledValue));
    }
}
