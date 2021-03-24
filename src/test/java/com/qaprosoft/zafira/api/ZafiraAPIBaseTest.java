package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.AbstractTest;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.mock.GetAllJobs;
import com.qaprosoft.zafira.api.mock.GetCreateJob;
import com.qaprosoft.zafira.api.mock.GetCreateJobBuild;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.service.impl.*;

import com.zebrunner.agent.core.registrar.CurrentTestRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class ZafiraAPIBaseTest extends AbstractTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    protected ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();


    @BeforeSuite
    protected void createJenkinsMockJob() {
        CurrentTestRun.setBuild(new MetadataServiceImpl().getBuild());
        String jenkinsMockJob = R.TESTDATA.get(ConfigConstant.JENKINS_MOCK_JOBNAME_KEY);
        GetAllJobs getAllJobs = new GetAllJobs();
        String allMockJobs = apiExecutor.callApiMethod(getAllJobs);
        if (!allMockJobs.contains(jenkinsMockJob)) {
            GetCreateJob getCreateJob = new GetCreateJob(jenkinsMockJob);
            apiExecutor.callApiMethod(getCreateJob);

            GetCreateJobBuild getCreateJobBuild = new GetCreateJobBuild(jenkinsMockJob);
            apiExecutor.callApiMethod(getCreateJobBuild);

            String allMockJobsAfterCreating = apiExecutor.callApiMethod(getAllJobs);
            Assert.assertTrue(allMockJobsAfterCreating.contains(jenkinsMockJob), "Expected job is not create");
        } else {
            Assert.assertTrue(allMockJobs.contains(jenkinsMockJob), "Expected job is not find");
        }
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

    protected int startTestRunV1() {
        TestRunServiceAPIImplV1 testRunServiceV1 = new TestRunServiceAPIImplV1();
        int testRunId = testRunServiceV1.start();
        return testRunId;
    }

    protected int startTestV1(int testRunId) {
        TestServiceV1Impl testServiceV1 = new TestServiceV1Impl();
        int testId = testServiceV1.startTest(testRunId);
        return testId;
    }

    protected List<Integer> startTestsV1(int testRunId, int numOfTests) {
        TestServiceV1Impl testServiceV1 = new TestServiceV1Impl();
        List<Integer> testIds = testServiceV1.startTests(testRunId, numOfTests);
        return testIds;
    }

    protected void deleteTestRunV1(int testRunId) {
        TestRunServiceAPIImplV1 testRunServiceV1 = new TestRunServiceAPIImplV1();
        testRunServiceV1.deleteTestRun(testRunId);
    }


    @AfterSuite
    protected void deleteAllTestRuns() {
        TestRunServiceAPIImpl testRunServiceAPIImpl = new TestRunServiceAPIImpl();
        String searchCriteriaType = R.TESTDATA.get(ConfigConstant.SEARCH_CRITERIA_TYPE_KEY);
        int testSuiteId = new TestSuiteServiceImpl().create();
        List<Integer> ids = testRunServiceAPIImpl.getAll(searchCriteriaType, testSuiteId);
        LOGGER.info(String.format("Test run IDs to delete: %s", ids.toString()));
        ids.forEach(testRunServiceAPIImpl::deleteById);
    }

}
