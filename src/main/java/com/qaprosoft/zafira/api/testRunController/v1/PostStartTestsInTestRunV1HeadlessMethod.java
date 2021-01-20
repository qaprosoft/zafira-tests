package com.qaprosoft.zafira.api.testRunController.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.time.OffsetDateTime;
import java.util.UUID;


public class PostStartTestsInTestRunV1HeadlessMethod extends ZafiraBaseApiMethodWithAuth {
        public PostStartTestsInTestRunV1HeadlessMethod(int testRunId) {
        super("api/test_run/v1/_post/for_tests_headless_rq.json", "api/test_run/v1/_post/for_tests_headless_rs.json",
                "api/test_runV1_test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
        addProperty("startedAt",OffsetDateTime.now());
    }
}
