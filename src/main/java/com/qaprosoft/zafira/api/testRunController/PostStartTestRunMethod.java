package com.qaprosoft.zafira.api.testRunController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;
import io.restassured.http.Header;

import java.util.UUID;

public class PostStartTestRunMethod extends ZafiraBaseApiMethodWithAuth {

    public PostStartTestRunMethod(int testSuiteId, int jobId) {
        super("api/test_run/_post/rq.json", "api/test_run/_post/rs.json", "api/test_run.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        request.header(new Header("Project","DEF"));
        addProperty("testSuiteId", String.valueOf(testSuiteId));
        addProperty("jobId", String.valueOf(jobId));
        addProperty("ciRunId", UUID.randomUUID());
    }
}
