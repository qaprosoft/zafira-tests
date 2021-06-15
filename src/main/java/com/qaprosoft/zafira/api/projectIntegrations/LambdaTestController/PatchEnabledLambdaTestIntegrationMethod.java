package com.qaprosoft.zafira.api.projectIntegrations.LambdaTestController;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/projectIntegrations/lambdaTest/_patch/rq.json")
@Endpoint(url = "${api_url}/v1/integrations/lambda-test?projectId=${projectId}", methodType = HttpMethodType.PATCH)
public class PatchEnabledLambdaTestIntegrationMethod extends ZafiraBaseApiMethodWithAuth {
    public PatchEnabledLambdaTestIntegrationMethod(int projectId, Boolean enabledValue) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));

        addProperty("value", String.valueOf(enabledValue));
    }

    public PatchEnabledLambdaTestIntegrationMethod(int projectId, Boolean enabledValue, String token) {
        super(token);
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
        addProperty("value", String.valueOf(enabledValue));
    }
}
