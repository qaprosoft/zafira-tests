package com.qaprosoft.zafira.api.projectTestRuns;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;


@Endpoint(url = "${api_url}/api/project-test-runs/${testRunId}", methodType = HttpMethodType.DELETE)
public class DeleteProjectTestRunByIdMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteProjectTestRunByIdMethod(int testRunId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
    }
}
