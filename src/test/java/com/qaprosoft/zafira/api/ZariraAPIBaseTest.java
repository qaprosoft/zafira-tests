package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.AbstractTest;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.service.impl.*;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;

import java.util.List;

public class ZariraAPIBaseTest extends AbstractTest {
    private static final Logger LOGGER = Logger.getLogger(ZariraAPIBaseTest.class);
    protected ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @AfterTest
    protected void deleteAllTestRun(){
        TestRunServiceAPIImpl testRunServiceAPIImpl = new TestRunServiceAPIImpl();
        String token = new APIContextManager().getAccessToken();
        String searchCriteriaType = R.TESTDATA.get(ConfigConstant.SEARCH_CRITERIA_TYPE_KEY);
        int testSuiteId = new TestSuiteServiceImpl().create(token);
        List<Integer> ids = testRunServiceAPIImpl.getAll(token, searchCriteriaType, testSuiteId);
        LOGGER.info(String.format("Test run IDs to delete: %s", ids.toString()));
        ids.forEach(id -> testRunServiceAPIImpl.deleteById(token, id));
    }

    protected int createTestRun(String accessToken, int numOfTests) {
        TestRunServiceAPIImpl testRunServiceImpl = new TestRunServiceAPIImpl();
        int testSuiteId = new TestSuiteServiceImpl().create(accessToken);
        int jobId = new JobServiceImpl().create(accessToken);
        int testRunId = testRunServiceImpl.create(accessToken, testSuiteId, jobId);
        int testCaseId = new TestCaseServiceImpl().create(accessToken, testSuiteId);

        for (int i = 0; i < numOfTests; ++i) {
            new TestServiceImpl().create(accessToken, testCaseId, testRunId);
        }
        return testRunId;
    }

}
