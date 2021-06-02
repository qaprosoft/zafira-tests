package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.testRunController.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.domain.EmailMsg;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.manager.EmailManager;
import com.qaprosoft.zafira.service.impl.*;
import com.qaprosoft.zafira.util.CryptoUtil;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * Test Run Controller
 */

public class TestRunTest extends ZafiraAPIBaseTest {

    private final EmailManager EMAIL = new EmailManager(
            CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.GMAIL_USERNAME_KEY)),
            CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.GMAIL_PASSWORD_KEY)));
    private final static Logger LOGGER = Logger.getLogger(TestRunTest.class);
    private final int TESTS_TO_ADD = 1;
    private final String TEST_STATUS_FAILED = "FAILED";
    private final String TEST_STATUS_ABORTED = "ABORTED";

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
        PutTestRunMethod putTestRunMethod = new PutTestRunMethod(testSuiteId, jobId, testRunId, TEST_STATUS_ABORTED);
        apiExecutor.expectStatus(putTestRunMethod, HTTPStatusCodeType.OK);
        String putTestRunRs = apiExecutor.callApiMethod(putTestRunMethod);
        apiExecutor.validateResponse(putTestRunMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String actualStatus = JsonPath.from(putTestRunRs).get(JSONConstant.STATUS_KEY);
        Assert.assertEquals(actualStatus, TEST_STATUS_ABORTED, "TestRun was not updated!");
    }

    @Test
    public void testGetTestRunById() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        GetTestRunByIdMethod getTestRunByIdMethod = new GetTestRunByIdMethod(testRunId);
        apiExecutor.expectStatus(getTestRunByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getTestRunByIdMethod);
        apiExecutor.validateResponse(getTestRunByIdMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetTestRunByCiRunId() {
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        String ciRunId = new TestRunServiceAPIImpl().createTestRunAndReturnCiRunId(testSuiteId, jobId);
        GetTestRunByCiRunIdMethod getTestRunByCiRunIdMethod = new GetTestRunByCiRunIdMethod(ciRunId);
        apiExecutor.expectStatus(getTestRunByCiRunIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getTestRunByCiRunIdMethod);
        apiExecutor.validateResponse(getTestRunByCiRunIdMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testDeleteTestRun() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        apiExecutor.callApiMethod(new DeleteTestRunMethod(testRunId));
        GetTestRunByIdMethod getTestRunByIdMethod = new GetTestRunByIdMethod(testRunId);
        apiExecutor.expectStatus(getTestRunByIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getTestRunByIdMethod);
    }

    @Test
    public void testFinishTestRun() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        String expectedTestRunStatus = R.TESTDATA.get(ConfigConstant.STATUS_EXPECTED_KEY);
        PostFinishTestRunMethod postFinishTestRunMethod = new PostFinishTestRunMethod(testRunId);
        apiExecutor.expectStatus(postFinishTestRunMethod,HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postFinishTestRunMethod);
        apiExecutor.validateResponse(postFinishTestRunMethod,JSONCompareMode.STRICT,JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String testRunStatus = JsonPath.from(rs).getString(JSONConstant.STATUS_KEY);
        Assert.assertEquals(testRunStatus, expectedTestRunStatus, "TestRun was not finish!");
    }

    @Test
    public void testSendTestRunResultEmail() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        String expStatus = new TestRunServiceAPIImpl().finishTestRun(testRunId);
        LOGGER.info("TestRun status to verify: ".concat(expStatus));
        new TestRunServiceAPIImpl().finishTestRun(testRunId);
        String expectedTestRunIdInMail = String.format("%s/%s/%s", APIContextManager.BASE_URL,
                R.TESTDATA.get(ConfigConstant.TEST_RUN_URL_PREFIX_KEY), testRunId);
        LOGGER.info("TestRun url to verify: ".concat(expectedTestRunIdInMail));
        PostTestRunResultEmailMethod postTestRunResultEmailMethod = new PostTestRunResultEmailMethod(testRunId);
        apiExecutor.expectStatus(postTestRunResultEmailMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postTestRunResultEmailMethod);
        verifyIfEmailWasDelivered(expStatus);
        EMAIL.deleteMsg(expStatus);
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
        apiExecutor.validateResponse(postAbortTestRunMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());

    }

    @Test(enabled = false)
    public void testAbortTestRunCi() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        String ciRunId = new TestRunServiceAPIImpl().getCiRunId(testRunId);
        GetAbortTestRunCiMethod getAbortTestRunCiMethod = new GetAbortTestRunCiMethod(testRunId, ciRunId);
        apiExecutor.expectStatus(getAbortTestRunCiMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAbortTestRunCiMethod);
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
     * Checks the expected testRun url contains in the last email
     *
     * @param expStatus expected testrun URL to get from email
     * @return
     */
    private boolean verifyIfEmailWasDelivered(String expStatus) {
        final int lastEmailIndex = 0;
        final int emailsCount = 1;
        LOGGER.info("Will get last email from inbox.");
        EMAIL.waitForEmailDelivered(new Date(), expStatus); // decency from connection, wait a little bit
        EmailMsg email = EMAIL.getInbox(emailsCount)[lastEmailIndex];
        return email.getContent().contains(expStatus);
    }

    @Test(enabled = false)
    public void testPostAIAnalysis() {
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testId = new TestServiceImpl().create(testCaseId, testRunId);
        new TestServiceImpl().updateTestStatus(testId, testSuiteId,
                jobId, TEST_STATUS_FAILED);
        PostAIAnalysisMethod postAIAnalysisMethod = new PostAIAnalysisMethod(testRunId);
        apiExecutor.expectStatus(postAIAnalysisMethod, HTTPStatusCodeType.ACCEPTED);
        apiExecutor.callApiMethod(postAIAnalysisMethod);
    }
}
