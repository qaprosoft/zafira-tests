package com.qaprosoft.zafira.api.projectTestRuns;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@Endpoint(url = "${api_url}/api/project-test-runs/${id}/milestone/${milestoneId}", methodType = HttpMethodType.PUT)
public class AttachTestRunToMilestoneMethod extends ZafiraBaseApiMethodWithAuth {
    public AttachTestRunToMilestoneMethod(int testRunId, int milestoneId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testRunId));
        replaceUrlPlaceholder("milestoneId", String.valueOf(milestoneId));
        addUrlParameter("projectId", String.valueOf(1));
    }
}
