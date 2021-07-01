package com.qaprosoft.zafira.api;


import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.testController.DeleteWorkItemMethod;
import com.qaprosoft.zafira.api.testController.GetWorkItemMethod;
import com.qaprosoft.zafira.api.testController.PostCreateBatchWorkItemsMethod;
import com.qaprosoft.zafira.api.testController.PostLinkWorkItemMethod;
import com.qaprosoft.zafira.api.testRunController.PostAIAnalysisMethod;
import com.qaprosoft.zafira.api.testRunController.PostMarkTestRunReviewedMethod;
import com.qaprosoft.zafira.api.testRunController.v1.DeleteTestRunByIdV1Method;
import com.qaprosoft.zafira.api.testRunController.v1.GetListTestRunsV1Method;
import com.qaprosoft.zafira.api.testRunController.v1.PostStartTestRunV1Method;
import com.qaprosoft.zafira.api.testRunController.v1.PutFinishTestRunV1Method;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.constant.TestRailConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceImpl;
import com.qaprosoft.zafira.service.impl.TestServiceV1Impl;
import com.zebrunner.agent.core.annotation.TestLabel;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Test Run Controller v1
 */

public class TestRunV1Test extends ZafiraAPIBaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestRunV1Test.class);
    private int testRunId;
    private static final String RESULT_SKIPPED = "SKIPPED";
    private static final String RESULT_FAILED = "FAILED";
    private static final String RESULT_PASSED = "PASSED";
    private static final String RESULT_ABORTED = "ABORTED";
    private static final String PROJECT_UNKNOWN = APIContextManager.PROJECT_NAME_KEY;
    private static final String EMPTY_PROJECT = "";

    @AfterMethod
    public void deleteTestRun() {
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
    }

    /**
     * Test run execution start
     */

    @DataProvider(name = "rqFieldsAndHttpStatusDataProvider")
    public Object[][] getMandatoryFieldsAndHttpStatus() {
        return new Object[][]{{"uuid", HTTPStatusCodeType.OK},
                {JSONConstant.NAME, HTTPStatusCodeType.BAD_REQUEST},
                {JSONConstant.STARTED_AT, HTTPStatusCodeType.BAD_REQUEST},
                {JSONConstant.FRAMEWORK, HTTPStatusCodeType.BAD_REQUEST}};
    }

    @Test(dataProvider = "rqFieldsAndHttpStatusDataProvider")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "39916")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "39915")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "39914")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "38921")
    public void testStartTestRunV1WithoutField(String field, HTTPStatusCodeType statusCode) {
        PostStartTestRunV1Method postStartTestRunV1Method = new PostStartTestRunV1Method(PROJECT_UNKNOWN, OffsetDateTime.now().toString());
        apiExecutor.expectStatus(postStartTestRunV1Method, statusCode);
        postStartTestRunV1Method.removeProperty(field);
        String rs = apiExecutor.callApiMethod(postStartTestRunV1Method);
        testRunId = validateResponseAndCheckExistenceInTestRun(postStartTestRunV1Method, statusCode, rs);
    }

    public int validateResponseAndCheckExistenceInTestRun(AbstractApiMethodV2 apiMethodV2, HTTPStatusCodeType statusCode, String response) {
        if (statusCode == HTTPStatusCodeType.OK) {
            apiExecutor.validateResponse(apiMethodV2, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
            testRunId = JsonPath.from(response).getInt(JSONConstant.ID_KEY);
            Assert.assertTrue(new TestRunServiceAPIImpl().getAllTestRunIds().contains(testRunId),
                    "The test run with id= " + testRunId + " has not been created!");
        }
        return testRunId;
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "39917")
    public void testStartTestRunV1WithoutTestsWithExistingUUID() {
        TestRunServiceAPIImplV1 testRunServiceAPIImplV1 = new TestRunServiceAPIImplV1();
        testRunId = testRunServiceAPIImplV1.start();
        String uuid = testRunServiceAPIImplV1.getCiRunId(testRunId);
        PostStartTestRunV1Method postStartTestRunV1Method1 = new PostStartTestRunV1Method(PROJECT_UNKNOWN, OffsetDateTime.now().toString());
        postStartTestRunV1Method1.addProperty(JSONConstant.UUID_KEY, uuid);
        apiExecutor.expectStatus(postStartTestRunV1Method1, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postStartTestRunV1Method1);
        Assert.assertTrue(new TestRunServiceAPIImpl().getAllTestRunIds().contains(testRunId),
                "The test run has not been created!");
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "38924")
    public void testStartTestRunV1() {
        PostStartTestRunV1Method postStartTestRunV1Method = new PostStartTestRunV1Method(PROJECT_UNKNOWN, OffsetDateTime.now().toString());
        apiExecutor.expectStatus(postStartTestRunV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postStartTestRunV1Method);
        apiExecutor.validateResponse(postStartTestRunV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        testRunId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        Assert.assertTrue(new TestRunServiceAPIImpl().getAllTestRunIds().contains(testRunId),
                "The test run has not been created!");
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "38926")
    public void testStartTestRunV1WithoutQuery() {
        PostStartTestRunV1Method postStartTestRunV1Method =
                new PostStartTestRunV1Method(PROJECT_UNKNOWN, OffsetDateTime.now().toString());
        String newMethodPath = postStartTestRunV1Method.getMethodPath()
                .substring(0, postStartTestRunV1Method.getMethodPath().lastIndexOf("?"));
        postStartTestRunV1Method.setMethodPath(newMethodPath);
        apiExecutor.expectStatus(postStartTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postStartTestRunV1Method);
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "38925")
    public void testStartTestRunV1WithEmptyProjectKey() {
        PostStartTestRunV1Method postStartTestRunV1Method
                = new PostStartTestRunV1Method(EMPTY_PROJECT, OffsetDateTime.now().toString());
        apiExecutor.expectStatus(postStartTestRunV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(postStartTestRunV1Method);
    }

    @DataProvider(name = "rqMandatoryFieldsDataProvider")
    public Object[][] getRqMandatoryFields() {
        return new Object[][]{{"name"}, {"startedAt"}, {"framework"}};
    }

    @Test(dataProvider = "rqMandatoryFieldsDataProvider")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "38928")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "38962")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "38963")
    public void testStartTestRunV1WithEmptyField(String field) {
        PostStartTestRunV1Method postStartTestRunV1Method
                = new PostStartTestRunV1Method(PROJECT_UNKNOWN, OffsetDateTime.now().toString());
        postStartTestRunV1Method.addProperty(field, "");
        apiExecutor.expectStatus(postStartTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postStartTestRunV1Method);
    }

    @DataProvider(name = "startedAtDataProvider")
    public Object[][] getStartedAt() {
        return new Object[][]{{"invalid", "!"},
                {"in future", OffsetDateTime.now().plusDays(5).toString()}};
    }

    @Test(dataProvider = "startedAtDataProvider")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "38981")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "38984")
    public void testStartTestRunV1WithDifferentStartedAt(String description, String date) {
        PostStartTestRunV1Method postStartTestRunV1Method = new PostStartTestRunV1Method(PROJECT_UNKNOWN, date);
        apiExecutor.expectStatus(postStartTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postStartTestRunV1Method);
    }

    /**
     * Test run execution finish
     */

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "39155")
    public void testFinishTestRunWithoutTest() {
        testRunId = new TestRunServiceAPIImplV1().start();
        PutFinishTestRunV1Method putFinishTestRunV1Method = new PutFinishTestRunV1Method(testRunId, OffsetDateTime.now().toString());
        apiExecutor.expectStatus(putFinishTestRunV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putFinishTestRunV1Method);
        apiExecutor.validateResponse(putFinishTestRunV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String testRunStatus = new TestRunServiceAPIImpl().getTestRunStatus(testRunId);
        Assert.assertEquals(testRunStatus, RESULT_SKIPPED, "Test run status was not as expected!");
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "39089")
    public void testFinishTestRunWithTestStatusINPROGRESS() {
        TestRunServiceAPIImplV1 testRunServiceAPIImplV1 = new TestRunServiceAPIImplV1();
        testRunId = testRunServiceAPIImplV1.start();
        int testId = new TestServiceV1Impl().startTest(testRunId);
        PutFinishTestRunV1Method putFinishTestRunV1Method = new PutFinishTestRunV1Method(testRunId, OffsetDateTime.now().toString());
        apiExecutor.expectStatus(putFinishTestRunV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putFinishTestRunV1Method);
        apiExecutor.validateResponse(putFinishTestRunV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String testResults = testRunServiceAPIImplV1.getTestResultsAfterFinishTestRun(testRunId);
        String actualTestStatus = testRunServiceAPIImplV1.getTestStatusAfterFinishTestRun(testResults, testRunId, testId);
        Assert.assertEquals(actualTestStatus, RESULT_SKIPPED, "Test run has been finished incorrect!");
        String actualTestRunStatus = new TestRunServiceAPIImpl().getTestRunStatus(testRunId);
        Assert.assertEquals(actualTestRunStatus, RESULT_FAILED, "Test run has been finished incorrect!");
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "39151")
    public void testFinishTestRunWithTestStatusPASSED() {
        TestRunServiceAPIImplV1 testRunServiceAPIImplV1 = new TestRunServiceAPIImplV1();
        testRunId = testRunServiceAPIImplV1.start();
        int testId = new TestServiceV1Impl().startTest(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, RESULT_PASSED);
        PutFinishTestRunV1Method putFinishTestRunV1Method = new PutFinishTestRunV1Method(testRunId, OffsetDateTime.now().toString());
        apiExecutor.expectStatus(putFinishTestRunV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putFinishTestRunV1Method);
        apiExecutor.validateResponse(putFinishTestRunV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String testResults = testRunServiceAPIImplV1.getTestResultsAfterFinishTestRun(testRunId);
        String actualTestStatus = testRunServiceAPIImplV1.getTestStatusAfterFinishTestRun(testResults, testRunId, testId);
        Assert.assertEquals(actualTestStatus, RESULT_PASSED, "Test run has been finished incorrect!");
        String actualTestRunStatus = new TestRunServiceAPIImpl().getTestRunStatus(testRunId);
        Assert.assertEquals(actualTestRunStatus, RESULT_PASSED, "Test run has been finished incorrect!");
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "39875")
    public void testFinishTestRunWithTestWithAllTestStatus() {
        TestRunServiceAPIImplV1 testRunServiceAPIImplV1 = new TestRunServiceAPIImplV1();
        testRunId = testRunServiceAPIImplV1.start();
        int testId = new TestServiceV1Impl().startTest(testRunId);
        int testId1 = new TestServiceV1Impl().startTest(testRunId);
        int testId2 = new TestServiceV1Impl().startTest(testRunId);
        int testId3 = new TestServiceV1Impl().startTest(testRunId);
        int testId4 = new TestServiceV1Impl().startTest(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId1, RESULT_PASSED);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId2, RESULT_ABORTED);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId3, RESULT_SKIPPED);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId4, RESULT_FAILED);
        PutFinishTestRunV1Method putFinishTestRunV1Method = new PutFinishTestRunV1Method(testRunId, OffsetDateTime.now().toString());
        apiExecutor.expectStatus(putFinishTestRunV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putFinishTestRunV1Method);
        String testResults = testRunServiceAPIImplV1.getTestResultsAfterFinishTestRun(testRunId);
        String actualStatus = testRunServiceAPIImplV1.getTestStatusAfterFinishTestRun(testResults, testRunId, testId);
        String actualStatus1 = testRunServiceAPIImplV1.getTestStatusAfterFinishTestRun(testResults, testRunId, testId1);
        String actualStatus2 = testRunServiceAPIImplV1.getTestStatusAfterFinishTestRun(testResults, testRunId, testId2);
        String actualStatus3 = testRunServiceAPIImplV1.getTestStatusAfterFinishTestRun(testResults, testRunId, testId3);
        String actualStatus4 = testRunServiceAPIImplV1.getTestStatusAfterFinishTestRun(testResults, testRunId, testId4);
        Assert.assertEquals(actualStatus, RESULT_SKIPPED, "Test execution with id =  " + testId + "  has been finished incorrect!");
        Assert.assertEquals(actualStatus1, RESULT_PASSED, "Test execution with id =  " + testId1 + "  has been finished incorrect!");
        Assert.assertEquals(actualStatus2, RESULT_ABORTED, "Test execution with id =  " + testId2 + "  has been finished incorrect!");
        Assert.assertEquals(actualStatus3, RESULT_SKIPPED, "Test execution with id =  " + testId3 + "  has been finished incorrect!");
        Assert.assertEquals(actualStatus4, RESULT_FAILED, "Test execution with id =  " + testId4 + "  has been finished incorrect!");
        String actualTestRunStatus = new TestRunServiceAPIImpl().getTestRunStatus(testRunId);
        Assert.assertEquals(actualTestRunStatus, RESULT_FAILED, "Test run has been finished incorrect!");
    }

    @Test(dataProvider = "startedAtDataProvider", description = "negative")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "39092")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "39091")
    public void testFinishTestRunWithEndedAt(String description, String endedAt) {
        testRunId = new TestRunServiceAPIImplV1().start();
        PutFinishTestRunV1Method putFinishTestRunV1Method = new PutFinishTestRunV1Method(testRunId, OffsetDateTime.now().toString());
        putFinishTestRunV1Method.addProperty(JSONConstant.ENDED_AT, endedAt);
        apiExecutor.expectStatus(putFinishTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putFinishTestRunV1Method);
    }

    @Test(description = "negative")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "39090")
    public void testFinishTestRunWithoutEndedAt() {
        testRunId = new TestRunServiceAPIImplV1().start();
        PutFinishTestRunV1Method putFinishTestRunV1Method = new PutFinishTestRunV1Method(testRunId, OffsetDateTime.now().toString());
        putFinishTestRunV1Method.removeProperty(JSONConstant.ENDED_AT);
        apiExecutor.expectStatus(putFinishTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putFinishTestRunV1Method);
    }

    /**
     * Delete testRun
     */

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "39024")
    public void testDeleteTestRun() {
        testRunId = new TestRunServiceAPIImplV1().start();
        new TestServiceV1Impl().startTest(testRunId);
        DeleteTestRunByIdV1Method deleteTestRunByIdV1Method = new DeleteTestRunByIdV1Method(testRunId);
        apiExecutor.expectStatus(deleteTestRunByIdV1Method, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteTestRunByIdV1Method);
        Assert.assertFalse(new TestRunServiceAPIImpl().getAllTestRunIds().contains(testRunId),
                "The test run with id =  " + testRunId + "   has not been deleted!");
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "39184")
    public void testDeleteTestRunWithNonExistentTestRunId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        new TestServiceV1Impl().startTest(testRunId);
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
        DeleteTestRunByIdV1Method deleteTestRunByIdV1Method = new DeleteTestRunByIdV1Method(testRunId);
        apiExecutor.expectStatus(deleteTestRunByIdV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteTestRunByIdV1Method);
    }

    /**
     * Finish testRun
     */

    @Test
    public void testGetTestRunStatusV1WithTestResultsFAILEDAndPASSED() {
        String methodName = "NewMethodName".concat(RandomStringUtils.randomAlphabetic(5));
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId1 = new TestServiceV1Impl().startTestWithMethodName(testRunId, methodName);
        int testId2 = new TestServiceV1Impl().startTest(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId1, RESULT_FAILED);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId2, RESULT_PASSED);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
        String actualResult = new TestRunServiceAPIImplV1().getTestRunResult(testRunId);
        Assert.assertEquals(actualResult, RESULT_FAILED, "Result is not as expected!");
    }

    @Test
    public void testGetTestRunStatusV1WithTestResultsPASSED() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId1 = new TestServiceV1Impl().startTest(testRunId);
        int testId2 = new TestServiceV1Impl().startTest(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId1, RESULT_PASSED);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId2, RESULT_PASSED);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
        String actualResult = new TestRunServiceAPIImplV1().getTestRunResult(testRunId);
        Assert.assertEquals(actualResult, RESULT_PASSED, "Result is not as expected!");
    }

    @Test
    public void testGetTestRunStatusV1WithTestResultsSKIPPEDAndPASSED() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId1 = new TestServiceV1Impl().startTest(testRunId);
        int testId2 = new TestServiceV1Impl().startTest(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId1, RESULT_SKIPPED);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId2, RESULT_PASSED);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
        String actualResult = new TestRunServiceAPIImplV1().getTestRunResult(testRunId);
        Assert.assertEquals(actualResult, RESULT_FAILED, "Result is not as expected!");
    }

    @Test(enabled = false)
    public void testGetListTestRuns() {
        testRunId = new TestRunServiceAPIImplV1().start();
        new TestServiceV1Impl().startTest(testRunId);
        GetListTestRunsV1Method getListTestRunsV1Method = new GetListTestRunsV1Method();
        apiExecutor.expectStatus(getListTestRunsV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getListTestRunsV1Method);
        apiExecutor.validateResponse(getListTestRunsV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        apiExecutor.validateResponseAgainstJSONSchema(getListTestRunsV1Method, "api/1.json");
    }

    @Test
    public void testMarkTestRunV1Reviewed() {
        testRunId = new TestRunServiceAPIImplV1().start();
        new TestRunServiceAPIImpl().finishTestRun(testRunId);
        PostMarkTestRunReviewedMethod postMarkTestRunReviewedMethod = new PostMarkTestRunReviewedMethod(testRunId);
        apiExecutor.expectStatus(postMarkTestRunReviewedMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postMarkTestRunReviewedMethod);
    }

    @Test
    public void testPostAIAnalysis() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int id = new TestServiceV1Impl().startTest(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, id, RESULT_FAILED);
        PostAIAnalysisMethod postAIAnalysisMethod = new PostAIAnalysisMethod(testRunId);
        apiExecutor.expectStatus(postAIAnalysisMethod, HTTPStatusCodeType.ACCEPTED);
        apiExecutor.callApiMethod(postAIAnalysisMethod);
    }

    @Test
    public void testGetTestRunStatusV1WithTestResultsFAILEDAndPASSEDWithWorkItem() {
        String methodName = "ToCheckWorkItemMethodName".concat(RandomStringUtils.random(15));
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId1 = new TestServiceV1Impl().startTestWithMethodName(testRunId, methodName);
        int testId2 = new TestServiceV1Impl().startTest(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId1, RESULT_FAILED);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId2, RESULT_PASSED);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
        new TestServiceImpl().linkWorkItem(testId1, 5);
        String actualResult = new TestRunServiceAPIImplV1().getTestRunResult(testRunId);

        Assert.assertEquals(actualResult, RESULT_PASSED, "Result is not as expected!");
    }

    @Test
    public void testGetTestRunStatusV1WithLinkedWithWorkItem() {
        String methodName = "NewMethodName".concat(RandomStringUtils.randomAlphabetic(5));

        testRunId = new TestRunServiceAPIImplV1().start();
        int testId1 = new TestServiceV1Impl().startTestWithMethodName(testRunId, methodName);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId1, RESULT_FAILED);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
        new TestServiceImpl().linkWorkItem(testId1, 5,"ZEB-3347");

        int testRunIdNew = new TestRunServiceAPIImplV1().start();
        int testId2 = new TestServiceV1Impl().startTestWithMethodName(testRunIdNew, methodName);
        new TestServiceV1Impl().finishTestAsResult(testRunIdNew, testId2, RESULT_FAILED);
        new TestRunServiceAPIImplV1().finishTestRun(testRunIdNew);
        String actualResult = new TestRunServiceAPIImplV1().getTestRunResult(testRunIdNew);

        Assert.assertEquals(actualResult, RESULT_PASSED, "Result is not as expected!");
        new TestRunServiceAPIImplV1().deleteTestRun(testRunIdNew);
    }

    @Test
    public void testGetTestRunStatusV1WithLinkedWithInvalidWorkItem() {
        String methodName = "NewMethodName".concat(RandomStringUtils.randomAlphabetic(5));

        testRunId = new TestRunServiceAPIImplV1().start();
        int testId1 = new TestServiceV1Impl().startTestWithMethodName(testRunId, methodName);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId1, RESULT_FAILED);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
        new TestServiceImpl().linkWorkItem(testId1, 5,"INVALID_ZEB-2787");

        int testRunIdNew = new TestRunServiceAPIImplV1().start();
        int testId2 = new TestServiceV1Impl().startTestWithMethodName(testRunIdNew, methodName);
        new TestServiceV1Impl().finishTestAsResult(testRunIdNew, testId2, RESULT_FAILED);
        new TestRunServiceAPIImplV1().finishTestRun(testRunIdNew);
        String actualResult = new TestRunServiceAPIImplV1().getTestRunResult(testRunIdNew);

        Assert.assertEquals(actualResult, RESULT_PASSED, "Result is not as expected!");
        new TestRunServiceAPIImplV1().deleteTestRun(testRunIdNew);
    }
}
