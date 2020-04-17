package com.qaprosoft.zafira.api;

import java.util.Date;

import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.testRun.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.domain.EmailMsg;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.enums.IntegrationGroupType;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.manager.EmailManager;
import com.qaprosoft.zafira.service.impl.*;
import com.qaprosoft.zafira.util.CryptoUtil;

public class TestRunTest extends ZafiraAPIBaseTest {

    private final EmailManager EMAIL = new EmailManager(
            CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.GMAIL_USERNAME_KEY)),
            CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.GMAIL_PASSWORD_KEY)));

    private final static Logger LOGGER = Logger.getLogger(TestRunTest.class);
    private final int TESTS_TO_ADD = 1;

    @Test
    public void testStartTestRun() {
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();

        PostStartTestRunMethod postStartTestRunMethod = new PostStartTestRunMethod(testSuiteId, jobId);
        apiExecutor.expectStatus(postStartTestRunMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postStartTestRunMethod);
        apiExecutor.validateResponse(postStartTestRunMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testUpdateTestRun() {
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        String expectedWorkItemValue = R.TESTDATA.get(ConfigConstant.WORK_ITEM_KEY);

        PutTestRunMethod putTestRunMethod = new PutTestRunMethod(testSuiteId, jobId, testRunId, expectedWorkItemValue);
        apiExecutor.expectStatus(putTestRunMethod, HTTPStatusCodeType.OK);
        String putTestRunRs = apiExecutor.callApiMethod(putTestRunMethod);
        apiExecutor.validateResponse(putTestRunMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String workItem = JsonPath.from(putTestRunRs).get(JSONConstant.WORK_ITEM_RS_KEY);
        Assert.assertEquals(workItem, expectedWorkItemValue, "Launcher was not updated!");
    }

    @Test
    public void testGetTestRun() {
        int testRunId = createTestRun(TESTS_TO_ADD);

        GetTestRunMethod getTestRunMethod = new GetTestRunMethod(testRunId);
        apiExecutor.expectStatus(getTestRunMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getTestRunMethod);
        apiExecutor.validateResponse(getTestRunMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testDeleteTestRun() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        apiExecutor.callApiMethod(new DeleteTestRunMethod(testRunId));

        GetTestRunMethod getTestRunMethod = new GetTestRunMethod(testRunId);
        apiExecutor.expectStatus(getTestRunMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getTestRunMethod);
    }

    @Test
    public void testFinishTestRun() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        String expectedTestRunStatus = R.TESTDATA.get(ConfigConstant.STATUS_EXPECTED_KEY);
        String testRunStatus = new TestRunServiceAPIImpl().finishTestRun(testRunId);
        Assert.assertEquals(testRunStatus, expectedTestRunStatus, "TestRun was not finish!");
    }

    @Test
    public void testSendTestRunResultEmail() {
        Assert.assertTrue(
                new IntegrationServiceImpl().isIntegrationEnabled(
                        R.TESTDATA.getInt(ConfigConstant.EMAIL_INTEGRATION_ID_KEY), IntegrationGroupType.MAIL),
                "Email integration disabled!");
        int testRunId = createTestRun(TESTS_TO_ADD);
        new TestRunServiceAPIImpl().finishTestRun(testRunId);
        String expectedTestRunIdInMail = String.format("%s/%s/%s", APIContextManager.BASE_URL,
                R.TESTDATA.get(ConfigConstant.TEST_RUN_URL_PREFIX_KEY), testRunId);
        new TestRunServiceAPIImpl().finishTestRun(testRunId);

        PostTestRunResultEmailMethod postTestRunResultEmailMethod = new PostTestRunResultEmailMethod(testRunId);
        apiExecutor.expectStatus(postTestRunResultEmailMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postTestRunResultEmailMethod);
        Assert.assertTrue(verifyIfEmailWasDelivered(expectedTestRunIdInMail), "Email was not delivered!");
    }

    @Test
    public void testGetTestRunResultHtmlText() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        new TestRunServiceAPIImpl().finishTestRun(testRunId);

        GetTestRunResultHtmlTextMethod getTestRunResultHtmlTextMethod = new GetTestRunResultHtmlTextMethod(testRunId);
        apiExecutor.expectStatus(getTestRunResultHtmlTextMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getTestRunResultHtmlTextMethod);
    }

    @Test
    public void testMarkTestRunReviewed() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        new TestRunServiceAPIImpl().finishTestRun(testRunId);

        PostMarkTestRunReviewedMethod postMarkTestRunReviewedMethod = new PostMarkTestRunReviewedMethod(testRunId);
        apiExecutor.expectStatus(postMarkTestRunReviewedMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postMarkTestRunReviewedMethod);
    }

    @Test
    public void testGetTestByTestRunId() {
        TestServiceImpl testServiceImpl = new TestServiceImpl();
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testId = testServiceImpl.create(testCaseId, testRunId);
        testServiceImpl.finishTest(testCaseId, testRunId, testId);

        GetTestByTestRunIdMethod getTestByTestRunIdMethod = new GetTestByTestRunIdMethod(testRunId);
        apiExecutor.expectStatus(getTestByTestRunIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getTestByTestRunIdMethod);
        apiExecutor.validateResponse(getTestByTestRunIdMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testBuildTestRunJob() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        new TestRunServiceAPIImpl().finishTestRun(testRunId);

        PostTestRunByJobMethod postTestRunByJobMethod = new PostTestRunByJobMethod(testRunId);
        apiExecutor.expectStatus(postTestRunByJobMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postTestRunByJobMethod);
    }

    @Test
    public void testDebugJob() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        new TestRunServiceAPIImpl().finishTestRun(testRunId);

        GetDebugJobMethod getDebugJobMethod = new GetDebugJobMethod(testRunId);
        apiExecutor.expectStatus(getDebugJobMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getDebugJobMethod);
    }

    @Test
    public void testGetTestRunJobParameters() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        new TestRunServiceAPIImpl().finishTestRun(testRunId);

        GetTestRunJobParametersMethod getTestRunJobParametersMethod = new GetTestRunJobParametersMethod(testRunId);
        apiExecutor.expectStatus(getTestRunJobParametersMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getTestRunJobParametersMethod);
        apiExecutor.validateResponse(getTestRunJobParametersMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @DataProvider(name = "rerunFailuresDataProvider")
    public Object[][] getRerunFailuresFlag() {
        return new Object[][]{{true}, {false}};
    }

    @Test(dataProvider = "rerunFailuresDataProvider")
    public void testGetTestRunJobById(boolean rerunFailures) {
        int testRunId = createTestRun(TESTS_TO_ADD);
        new TestRunServiceAPIImpl().finishTestRun(testRunId);

        GetRerunTestRunByIdMethod getRerunTestRunByIdMethod = new GetRerunTestRunByIdMethod(testRunId, rerunFailures);
        apiExecutor.expectStatus(getRerunTestRunByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getRerunTestRunByIdMethod);
        apiExecutor.validateResponse(getRerunTestRunByIdMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test(dataProvider = "rerunFailuresDataProvider")
    public void testRerunTestRunJob(boolean rerunFailures) {
        int testRunId = createTestRun(TESTS_TO_ADD);
        new TestRunServiceAPIImpl().finishTestRun(testRunId);

        PostRerunTestRunJobsMethod postRerunTestRunJobsMethod = new PostRerunTestRunJobsMethod(rerunFailures);
        apiExecutor.expectStatus(postRerunTestRunJobsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postRerunTestRunJobsMethod);
    }

    @Test
    public void testAbortTestRun() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        String ciRunId = new TestRunServiceAPIImpl().getCiRunId(testRunId);

        PostAbortTestRunMethod postAbortTestRunMethod = new PostAbortTestRunMethod(testRunId, ciRunId);
        apiExecutor.expectStatus(postAbortTestRunMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postAbortTestRunMethod);
    }

    @Test
    public void testAbortTestRunCi() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        String ciRunId = new TestRunServiceAPIImpl().getCiRunId(testRunId);

        GetAbortTestRunCiMethod getAbortTestRunCiMethod = new GetAbortTestRunCiMethod(testRunId, ciRunId);
        apiExecutor.expectStatus(getAbortTestRunCiMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAbortTestRunCiMethod);
    }

    @Test
    public void testAbortTestRunDebug() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        String ciRunId = new TestRunServiceAPIImpl().getCiRunId(testRunId);

        GetAbortDebugTestRunMethod getAbortDebugTestRunMethod = new GetAbortDebugTestRunMethod(testRunId, ciRunId);
        apiExecutor.expectStatus(getAbortDebugTestRunMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAbortDebugTestRunMethod);
    }

    @Test
    public void testGetBuildConsoleOutput() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        String ciRunId = new TestRunServiceAPIImpl().getCiRunId(testRunId);
        GetBuildConsoleOutputMethod getBuildConsoleOutputMethod = new GetBuildConsoleOutputMethod(ciRunId);
        apiExecutor.expectStatus(getBuildConsoleOutputMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getBuildConsoleOutputMethod);
    }

    @Test
    public void testGetTestRunBySearchCriteria() {
        String searchCriteriaType = R.TESTDATA.get(ConfigConstant.SEARCH_CRITERIA_TYPE_KEY);
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        apiExecutor.callApiMethod(new PostStartTestRunMethod(testSuiteId, jobId));

        GetTestRunBySearchCriteriaMethod getTestRunBySearchCriteriaMethod = new GetTestRunBySearchCriteriaMethod(searchCriteriaType, testSuiteId);
        apiExecutor.expectStatus(getTestRunBySearchCriteriaMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getTestRunBySearchCriteriaMethod);
        apiExecutor.validateResponse(getTestRunBySearchCriteriaMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    /**
     * Checks the expected testrun url contains in the last email
     *
     * @param testrunURL expected testrun URL to get from email
     * @return
     */
    private boolean verifyIfEmailWasDelivered(String testrunURL) {
        final int lastEmailIndex = 0;
        final int emailsCount = 1;
        LOGGER.info("Will get last email from inbox.");
        EMAIL.waitForEmailDelivered(new Date(), testrunURL); // decency from connection, wait a little bit
        EmailMsg email = EMAIL.getInbox(emailsCount)[lastEmailIndex];
        return email.getContent().contains(testrunURL);
    }
}
