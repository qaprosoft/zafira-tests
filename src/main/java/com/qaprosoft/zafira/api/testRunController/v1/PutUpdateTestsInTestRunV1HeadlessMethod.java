package com.qaprosoft.zafira.api.testRunController.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.time.OffsetDateTime;


public class PutUpdateTestsInTestRunV1HeadlessMethod extends ZafiraBaseApiMethodWithAuth {
        public PutUpdateTestsInTestRunV1HeadlessMethod(int testRunId,int testId) {
        super("api/test_run/v1/_put/for_tests_headless_rq.json", "api/test_run/v1/_put/for_tests_headless_rs.json",
                "api/test_runV1_test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
        replaceUrlPlaceholder("testId", String.valueOf(testId));
    }
}
