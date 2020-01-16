package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.UUID;

public class PostStartTestRunMethod extends AbstractApiMethodV2 {

    public PostStartTestRunMethod(String accessToken, int testSuiteId, int jobId) {
        super("api/test_run/_post/rq.json", "api/test_run/_post/rs.json", "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("Authorization=Bearer " + accessToken);
        setHeaders("Project=UNKNOWN");
        addProperty("testSuiteId", testSuiteId);
        addProperty("jobId", jobId);
        addProperty("ciRunId", UUID.randomUUID());
    }
}
