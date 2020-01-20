package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.AbstractTest;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.service.impl.*;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;

import java.util.List;

public class ZariraAPIBaseTest extends AbstractTest {
    private static final Logger LOGGER = Logger.getLogger(ZariraAPIBaseTest.class);
    protected ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @AfterSuite
    protected void deleteAllTestRuns() {
        TestRunServiceAPIImpl testRunServiceAPIImpl = new TestRunServiceAPIImpl();
        String searchCriteriaType = R.TESTDATA.get(ConfigConstant.SEARCH_CRITERIA_TYPE_KEY);
        int testSuiteId = new TestSuiteServiceImpl().create();
        List<Integer> ids = testRunServiceAPIImpl.getAll(searchCriteriaType, testSuiteId);
        LOGGER.info(String.format("Test run IDs to delete: %s", ids.toString()));
        ids.forEach(testRunServiceAPIImpl::deleteById);
    }

    protected int createTestRun(int numOfTests) {
        TestRunServiceAPIImpl testRunServiceImpl = new TestRunServiceAPIImpl();
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testRunId = testRunServiceImpl.create(testSuiteId, jobId);
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);

        for (int i = 0; i < numOfTests; ++i) {
            new TestServiceImpl().create(testCaseId, testRunId);
        }
        return testRunId;
    }

}
