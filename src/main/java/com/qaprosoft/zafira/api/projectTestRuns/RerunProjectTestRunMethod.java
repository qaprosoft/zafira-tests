package com.qaprosoft.zafira.api.projectTestRuns;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/projectTestRuns/_post/rq_for_rerun.json")
@Endpoint(url = "${api_url}/api/project-test-runs/${testRunId}/rerun", methodType = HttpMethodType.GET)
public class RerunProjectTestRunMethod extends ZafiraBaseApiMethodWithAuth {
    public RerunProjectTestRunMethod(int testRunId, Boolean rerun_failures) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
        addProperty("rerun_failures", rerun_failures);
    }
}
