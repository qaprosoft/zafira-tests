package com.qaprosoft.zafira.api.projectTestRuns;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@ResponseTemplatePath(path = "api/projectTestRuns/_get/rs_by_testRunId.json")
@Endpoint(url = "${api_url}/api/project-test-runs/${testRunId}", methodType = HttpMethodType.GET)
public class GetProjectTestRunByTestRunIdMethod extends ZafiraBaseApiMethodWithAuth {
    public GetProjectTestRunByTestRunIdMethod(int testRunId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
    }
}
