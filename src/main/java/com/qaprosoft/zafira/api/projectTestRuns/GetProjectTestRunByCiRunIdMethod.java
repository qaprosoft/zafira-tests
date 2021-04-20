package com.qaprosoft.zafira.api.projectTestRuns;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@ResponseTemplatePath(path = "api/projectTestRuns/_get/rs_by_ciRunId.json")
@Endpoint(url = "${api_url}/api/project-test-runs?ciRunId=${ciRunId}", methodType = HttpMethodType.GET)
public class GetProjectTestRunByCiRunIdMethod extends ZafiraBaseApiMethodWithAuth {
    public GetProjectTestRunByCiRunIdMethod(String ciRunId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("ciRunId", ciRunId);
    }
}
