package com.qaprosoft.zafira.api.projectTestRuns;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@Endpoint(url = "${api_url}/api/project-test-runs/abort/ci", methodType = HttpMethodType.GET)
public class AbortCIJobMethod extends ZafiraBaseApiMethodWithAuth {
    public AbortCIJobMethod(String ciRunId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        addUrlParameter("ciRunId", ciRunId);
    }
}
