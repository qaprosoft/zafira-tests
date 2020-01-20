package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.constant.TimeConstant;
import com.qaprosoft.zafira.domain.EmailMsg;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.enums.IntegrationGroupType;
import com.qaprosoft.zafira.manager.BashExecutorManager;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.manager.EmailManager;
import com.qaprosoft.zafira.service.impl.*;
import com.qaprosoft.zafira.util.CryptoUtil;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;

public class TestRunTest extends ZariraAPIBaseTest {

    private final EmailManager EMAIL = new EmailManager(
            CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.GMAIL_USERNAME_KEY)),
            CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.GMAIL_PASSWORD_KEY)));

    private final static Logger LOGGER = Logger.getLogger(TestRunTest.class);

    private final int TESTS_TO_ADD = 1;

    @BeforeTest
    public void startServer() {
        BashExecutorManager.getInstance().initJenkinsMockServerWithData();
    }

    @Test
    public void testStartTestRun() {
        String token = new APIContextManager().getAccessToken();
        int testSuiteId = new TestSuiteServiceImpl().create(token);
        int jobId = new JobServiceImpl().create(token);
        apiExecutor.callApiMethod(new PostStartTestRunMethod(token, testSuiteId, jobId), HTTPStatusCodeType.OK, true,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testUpdateTestRun() {
        String token = new APIContextManager().getAccessToken();
        int testSuiteId = new TestSuiteServiceImpl().create(token);
        int jobId = new JobServiceImpl().create(token);
        int testRunId = new TestRunServiceAPIImpl().create(token, testSuiteId, jobId);
        String expectedWorkItemValue = R.TESTDATA.get(ConfigConstant.WORK_ITEM_KEY);
        String putLauncherRs = apiExecutor.callApiMethod(
                new PutTestRunMethod(token, testSuiteId, jobId, testRunId, expectedWorkItemValue),
                HTTPStatusCodeType.OK, true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String workItem = JsonPath.from(putLauncherRs).get(JSONConstant.WORK_ITEM_RS_KEY);
        Assert.assertEquals(workItem, expectedWorkItemValue, "Launcher was not updated!");
    }

    @Test
    public void testGetTestRun() {
        String token = new APIContextManager().getAccessToken();
        int testRunId = createTestRun(token, TESTS_TO_ADD);
        apiExecutor.callApiMethod(new GetTestRunMethod(token, testRunId), HTTPStatusCodeType.OK, true,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testDeleteTestRun() {
        String token = new APIContextManager().getAccessToken();
        int testRunId = createTestRun(token, TESTS_TO_ADD);
        apiExecutor.callApiMethod(new DeleteTestRunMethod(token, testRunId), HTTPStatusCodeType.OK, false, null);
        apiExecutor.callApiMethod(new GetTestRunMethod(token, testRunId), HTTPStatusCodeType.NOT_FOUND, false, null);

    }

    @Test
    public void testFinishTestRun() {
        String token = new APIContextManager().getAccessToken();
        int testRunId = createTestRun(token, TESTS_TO_ADD);
        String expectedTestRunStatus = R.TESTDATA.get(ConfigConstant.STATUS_EXPECTED_KEY);
        String testRunStatus = new TestRunServiceAPIImpl().finishTestRun(token, testRunId);
        Assert.assertEquals(testRunStatus, expectedTestRunStatus, "TestRun was not finish!");
    }

    @Test
    public void testSendTestRunResultEmail() {
        String token = new APIContextManager().getAccessToken();
        Assert.assertTrue(
                new IntegrationServiceImpl().isIntegrationEnabled(token,
                        R.TESTDATA.getInt(ConfigConstant.EMAIL_INTEGRATION_ID_KEY), IntegrationGroupType.MAIL),
                "Email integration disabled!");
        int testRunId = createTestRun(token, TESTS_TO_ADD);
        new TestRunServiceAPIImpl().finishTestRun(token, testRunId);
        String expectedTestRunIdInMail = String.format("%s/%s/%s", APIContextManager.BASE_URL,
                R.TESTDATA.get(ConfigConstant.TEST_RUN_URL_PREFIX_KEY), testRunId);
        new TestRunServiceAPIImpl().finishTestRun(token, testRunId);
        apiExecutor.callApiMethod(new PostTestRunResultEmailMethod(token, testRunId), HTTPStatusCodeType.OK, false,
                null);
        Assert.assertTrue(verifyIfEmailWasDelivered(expectedTestRunIdInMail), "Email was not delivered!");
    }

    @Test
    public void testGetTestRunResultHtmlText() {
        String token = new APIContextManager().getAccessToken();
        int testRunId = createTestRun(token, TESTS_TO_ADD);
        new TestRunServiceAPIImpl().finishTestRun(token, testRunId);
        apiExecutor.callApiMethod(new GetTestRunResultHtmlTextMethod(token, testRunId), HTTPStatusCodeType.OK, false,
                null);
    }

    @Test
    public void testMarkTestRunReviewed() {
        String token = new APIContextManager().getAccessToken();
        int testRunId = createTestRun(token, TESTS_TO_ADD);
        new TestRunServiceAPIImpl().finishTestRun(token, testRunId);
        apiExecutor.callApiMethod(new PostMarkTestRunReviewedMethod(token, testRunId), HTTPStatusCodeType.OK, false,
                null);
    }

    @Test
    public void testGetTestByTestRunId() {
        TestServiceImpl testServiceImpl = new TestServiceImpl();
        String token = new APIContextManager().getAccessToken();
        int testSuiteId = new TestSuiteServiceImpl().create(token);
        int jobId = new JobServiceImpl().create(token);
        int testRunId = new TestRunServiceAPIImpl().create(token, testSuiteId, jobId);
        int testCaseId = new TestCaseServiceImpl().create(token, testSuiteId);
        int testId = testServiceImpl.create(token, testCaseId, testRunId);
        testServiceImpl.finishTest(token, testCaseId, testRunId, testId);
        apiExecutor.callApiMethod(new GetTestByTestRunIdMethod(token, testRunId), HTTPStatusCodeType.OK, true,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test(enabled = false) //TODO: enable this test when jenkins mock container will be up
    public void testBuildTestRunJob() {
        String token = new APIContextManager().getAccessToken();
        int testRunId = createTestRun(token, TESTS_TO_ADD);
        new TestRunServiceAPIImpl().finishTestRun(token, testRunId);
        apiExecutor.callApiMethod(new PostTestRunByJobMethod(token, testRunId), HTTPStatusCodeType.OK, false, null);
    }

    @Test(enabled = false) //TODO: enable this test when jenkins mock container will be up
    public void testDebugJob() {
        String token = new APIContextManager().getAccessToken();
        int testRunId = createTestRun(token, TESTS_TO_ADD);
        new TestRunServiceAPIImpl().finishTestRun(token, testRunId);

        apiExecutor.callApiMethod(new GetDebugJobMethod(token, testRunId), HTTPStatusCodeType.OK, false, null);
    }

    @Test(enabled = false) //TODO: enable this test when jenkins mock container will be up
    public void testGetTestRunJobParameters() {
        String token = new APIContextManager().getAccessToken();
        int testRunId = createTestRun(token, TESTS_TO_ADD);
        new TestRunServiceAPIImpl().finishTestRun(token, testRunId);

        apiExecutor.callApiMethod(new GetTestRunJobParametersMethod(token, testRunId), HTTPStatusCodeType.OK, true,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @DataProvider(name = "rerunFailuresDataProvider")
    public Object[][] getRerunFailuresFlag() {
        return new Object[][]{{true}, {false}};
    }

    @Test(dataProvider = "rerunFailuresDataProvider", enabled = false) //TODO: enable this test when jenkins mock container will be up)
    public void testGetTestRunJobById(boolean rerunFailures) {
        String token = new APIContextManager().getAccessToken();
        int testRunId = createTestRun(token, TESTS_TO_ADD);
        new TestRunServiceAPIImpl().finishTestRun(token, testRunId);

        apiExecutor.callApiMethod(new GetRerunTestRunByIdMethod(token, testRunId, rerunFailures), HTTPStatusCodeType.OK,
                true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test(dataProvider = "rerunFailuresDataProvider", enabled = false) //TODO: enable this test when jenkins mock container will be up)
    public void testRerunTestRunJob(boolean rerunFailures) {
        String token = new APIContextManager().getAccessToken();
        int testRunId = createTestRun(token, TESTS_TO_ADD);
        new TestRunServiceAPIImpl().finishTestRun(token, testRunId);

        apiExecutor.callApiMethod(new PostRerunTestRunJobsMethod(token, rerunFailures), HTTPStatusCodeType.OK, false,
                null);
    }

    @Test(enabled = false) //TODO: enable this test when jenkins mock container will be up
    public void testAbortTestRun() {
        String token = new APIContextManager().getAccessToken();
        int testRunId = createTestRun(token, TESTS_TO_ADD);
        String ciRunId = new TestRunServiceAPIImpl().getCiRunId(token, testRunId);
        apiExecutor.callApiMethod(new PostAbortTestRunMethod(token, testRunId, ciRunId), HTTPStatusCodeType.OK, false,
                null);
    }

    @Test(enabled = false) //TODO: enable this test when jenkins mock container will be up
    public void testAbortTestRunCi() {
        String token = new APIContextManager().getAccessToken();
        int testRunId = createTestRun(token, TESTS_TO_ADD);
        String ciRunId = new TestRunServiceAPIImpl().getCiRunId(token, testRunId);
        apiExecutor.callApiMethod(new GetAbortTestRunCiMethod(token, testRunId, ciRunId), HTTPStatusCodeType.OK, false,
                null);
    }

    @Test(enabled = false) //TODO: enable this test when jenkins mock container will be up
    public void testAbortTestRunDebug() {
        String token = new APIContextManager().getAccessToken();
        int testRunId = createTestRun(token, TESTS_TO_ADD);
        String ciRunId = new TestRunServiceAPIImpl().getCiRunId(token, testRunId);
        apiExecutor.callApiMethod(new GetAbortDebugTestRunMethod(token, testRunId, ciRunId), HTTPStatusCodeType.OK,
                false, null);
    }

    @Test(enabled = false) //TODO: enable this test when jenkins mock container will be up
    public void testGetBuildConsoleOutput() {
        String token = new APIContextManager().getAccessToken();
        int testRunId = createTestRun(token, TESTS_TO_ADD);
        String ciRunId = new TestRunServiceAPIImpl().getCiRunId(token, testRunId);
        apiExecutor.callApiMethod(new GetBuildConsoleOutputMethod(token, ciRunId), HTTPStatusCodeType.OK, false, null);
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
