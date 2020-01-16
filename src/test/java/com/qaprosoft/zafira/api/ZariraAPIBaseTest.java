package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.AbstractTest;
import com.qaprosoft.zafira.service.impl.*;
import org.apache.log4j.Logger;

public class ZariraAPIBaseTest extends AbstractTest {
    private static final Logger LOGGER = Logger.getLogger(ZariraAPIBaseTest.class);
    protected ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    protected int createTestRun(String accessToken, int numOfTests) {
        TestRunServiceAPIImpl testRunServiceImpl = new TestRunServiceAPIImpl();
        int testSuiteId = new TestSuiteServiceImpl().getId(accessToken);
        int jobId = new JobServiceImpl().getId(accessToken);
        int testRunId = testRunServiceImpl.getId(accessToken, testSuiteId, jobId);
        int testCaseId = new TestCaseServiceImpl().getId(accessToken, testSuiteId);

        for (int i = 0; i < numOfTests; ++i) {
            new TestServiceImpl().getId(accessToken, testCaseId, testRunId);
        }
        return testRunId;
    }

}
