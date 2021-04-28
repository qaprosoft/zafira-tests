package com.qaprosoft.zafira.api.projectTestRuns;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@ResponseTemplatePath(path = "api/projectTestRuns/_post/rs_for_abort.json")
@RequestTemplatePath(path = "api/projectTestRuns/_post/rq_for_abort.json")
@Endpoint(url = "${api_url}/api/project-test-runs/abort", methodType = HttpMethodType.POST)
public class AbortProjectTestRunMethod extends ZafiraBaseApiMethodWithAuth {
    public AbortProjectTestRunMethod(String ciRunId, String comment) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        addProperty("comment", comment);
        addUrlParameter("ciRunId", ciRunId);
    }
}
