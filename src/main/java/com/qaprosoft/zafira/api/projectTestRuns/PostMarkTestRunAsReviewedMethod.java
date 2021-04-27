package com.qaprosoft.zafira.api.projectTestRuns;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/projectTestRuns/_post/rq_to_mark_reviewed.json")
@Endpoint(url = "${api_url}/api/project-test-runs/${testRunId}/markReviewed", methodType = HttpMethodType.POST)
public class PostMarkTestRunAsReviewedMethod extends ZafiraBaseApiMethodWithAuth {
    public PostMarkTestRunAsReviewedMethod(int testRunId, String comment) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
        addProperty("comment", comment);
    }
}
