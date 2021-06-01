package com.qaprosoft.zafira.api.projectIntegrations.qTest;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;


@Endpoint(url = "${api_url}/v1/integrations/qtest?projectId=${projectId}", methodType = HttpMethodType.DELETE)
public class DeleteQTestIntegrationMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteQTestIntegrationMethod(int projectId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
    }

    public DeleteQTestIntegrationMethod(int projectId, String token) {
        super(token);
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
    }
}
