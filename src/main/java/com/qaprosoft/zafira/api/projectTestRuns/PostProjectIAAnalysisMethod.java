package com.qaprosoft.zafira.api.projectTestRuns;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@Endpoint(url = "${api_url}/api/project-test-runs/${testRunId}/ai-analysis-request", methodType = HttpMethodType.POST)
public class PostProjectIAAnalysisMethod extends ZafiraBaseApiMethodWithAuth {
    public PostProjectIAAnalysisMethod(int testRunId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
    }
}
