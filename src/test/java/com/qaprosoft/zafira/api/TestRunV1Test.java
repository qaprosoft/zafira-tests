package com.qaprosoft.zafira.api;


import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.api.testRunController.PostAIAnalysisMethod;
import com.qaprosoft.zafira.api.testRunController.PostMarkTestRunReviewedMethod;
import com.qaprosoft.zafira.api.testRunController.v1.*;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.service.impl.*;
import io.restassured.path.json.JsonPath;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.OffsetDateTime;
import java.util.Locale;

/**
 * Test Run Controller v1
 */

public class TestRunV1Test extends ZafiraAPIBaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestRunV1Test.class);
    private int testRunId;
    private static final String RESULT_SKIPPED = "SKIPPED";
    private static final String RESULT_FAILED = "FAILED";
    private static final String RESULT_PASSED = "PASSED";
    private static final String RESULT_IN_PROGRESS = "IN_PROGRESS";
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
                {"name", HTTPStatusCodeType.BAD_REQUEST},
                {"startedAt", HTTPStatusCodeType.BAD_REQUEST},
                {"framework", HTTPStatusCodeType.BAD_REQUEST}};
    }

    @Test(dataProvider = "rqFieldsAndHttpStatusDataProvider")
    public void testStartTestRunV1WithoutField(String field, HTTPStatusCodeType statusCode) {
        PostStartTestRunV1Method postStartTestRunV1Method = new PostStartTestRunV1Method(PROJECT_UNKNOWN, OffsetDateTime.now().toString());
        apiExecutor.expectStatus(postStartTestRunV1Method, statusCode);
        postStartTestRunV1Method.removeProperty(field);
        String rs = apiExecutor.callApiMethod(postStartTestRunV1Method);
        testRunId = validateResponse(postStartTestRunV1Method, statusCode, rs);
    }

    public int validateResponse(AbstractApiMethodV2 apiMethodV2, HTTPStatusCodeType statusCode, String response) {
        if (statusCode == HTTPStatusCodeType.OK) {
            apiExecutor.validateResponse(apiMethodV2, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
            testRunId = JsonPath.from(response).getInt(JSONConstant.ID_KEY);
        }
        return testRunId;
    }

    @Test
    public void testStartTestRunV1WithoutTests() {
        PostStartTestRunV1Method postStartTestRunV1Method = new PostStartTestRunV1Method(PROJECT_UNKNOWN, OffsetDateTime.now().toString());
        apiExecutor.expectStatus(postStartTestRunV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postStartTestRunV1Method);
        apiExecutor.validateResponse(postStartTestRunV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        testRunId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
    }

    @Test()
    public void testStartTestRunV1WithoutQuery() {
        PostStartTestRunV1Method postStartTestRunV1Method = new PostStartTestRunV1Method(PROJECT_UNKNOWN, OffsetDateTime.now().toString());
        String newMethodPath = postStartTestRunV1Method.getMethodPath().substring(0, postStartTestRunV1Method.getMethodPath().lastIndexOf("?"));
        postStartTestRunV1Method.setMethodPath(newMethodPath);
        apiExecutor.expectStatus(postStartTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postStartTestRunV1Method);
    }

    @Test()
    public void testStartTestRunV1WithEmptyProjectKey() {
        PostStartTestRunV1Method postStartTestRunV1Method = new PostStartTestRunV1Method(EMPTY_PROJECT, OffsetDateTime.now().toString());
        apiExecutor.expectStatus(postStartTestRunV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(postStartTestRunV1Method);
    }

    @DataProvider(name = "rqMandatoryFieldsDataProvider")
    public Object[][] getRqMandatoryFields() {
        return new Object[][]{{"name"}, {"startedAt"}, {"framework"}};
    }

    @Test(dataProvider = "rqMandatoryFieldsDataProvider")
    public void testStartTestRunV1WithEmptyField(String field) {
        PostStartTestRunV1Method postStartTestRunV1Method = new PostStartTestRunV1Method(PROJECT_UNKNOWN, OffsetDateTime.now().toString());
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
    public void testStartTestRunV1WithDifferentStartedAt(String description, String date) {
        PostStartTestRunV1Method postStartTestRunV1Method = new PostStartTestRunV1Method(PROJECT_UNKNOWN, date);
        apiExecutor.expectStatus(postStartTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postStartTestRunV1Method);
    }

    /**
     * Test execution start
     */

    @Test
    public void testStartTestsInTestRunV1() {
        testRunId = new TestRunServiceAPIImplV1().create();
        PostStartTestsInTestRunV1Method postStartTestsInTestRunV1Method = new PostStartTestsInTestRunV1Method(testRunId);
        apiExecutor.expectStatus(postStartTestsInTestRunV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postStartTestsInTestRunV1Method);
        apiExecutor.validateResponse(postStartTestsInTestRunV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test(dataProvider = "rqForTestMandatoryFieldsDataProvider")
    public void testStartTestsInTestRunV1WithoutField(String field) {
        testRunId = new TestRunServiceAPIImplV1().create();
        PostStartTestsInTestRunV1Method postStartTestsInTestRunV1Method = new PostStartTestsInTestRunV1Method(testRunId);
        postStartTestsInTestRunV1Method.removeProperty(field);
        apiExecutor.expectStatus(postStartTestsInTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postStartTestsInTestRunV1Method);
    }

    @DataProvider(name = "rqForTestMandatoryFieldsDataProvider")
    public Object[][] getRqForTestMandatoryFields() {
        return new Object[][]{{"name"}, {"className"}, {"methodName"}, {"startedAt"}};
    }

    @Test(dataProvider = "startedAtDataProvider")
    public void testStartTestWithDifferentStartedAt(String description, String date) {
        testRunId = new TestRunServiceAPIImplV1().create();
        PostStartTestsInTestRunV1Method postStartTestMethod = new PostStartTestsInTestRunV1Method(testRunId);
        postStartTestMethod.addProperty("startedAt", date);
        apiExecutor.expectStatus(postStartTestMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postStartTestMethod);
    }

    @Test(dataProvider = "rqForTestMandatoryFieldsDataProvider")
    public void testStartTestWithEmptyMandatoryField(String field) {
        testRunId = new TestRunServiceAPIImplV1().create();
        PostStartTestsInTestRunV1Method postStartTestMethod = new PostStartTestsInTestRunV1Method(testRunId);
        postStartTestMethod.addProperty(field, "");
        apiExecutor.expectStatus(postStartTestMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postStartTestMethod);
    }

    @Test
    public void testStartTestsInTestRunV1WithNonExistentTestRunId() {
        TestRunServiceAPIImplV1 testRunServiceAPIImplV1 = new TestRunServiceAPIImplV1();
        testRunId = testRunServiceAPIImplV1.create();
        testRunServiceAPIImplV1.deleteTestRun(testRunId);
        PostStartTestsInTestRunV1Method postStartTestsInTestRunV1Method = new PostStartTestsInTestRunV1Method(testRunId);
        apiExecutor.expectStatus(postStartTestsInTestRunV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(postStartTestsInTestRunV1Method);
    }

    @Test
    public void testStartTestsInTestRunV1WithInvalidTestRunId() {
        testRunId = new TestRunServiceAPIImplV1().create();
        PostStartTestsInTestRunV1Method postStartTestsInTestRunV1Method = new PostStartTestsInTestRunV1Method(testRunId);
        String methodPath = postStartTestsInTestRunV1Method.getMethodPath().replace(String.valueOf(testRunId), "!");
        postStartTestsInTestRunV1Method.setMethodPath(methodPath);
        apiExecutor.expectStatus(postStartTestsInTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postStartTestsInTestRunV1Method);
    }

    /**
     * Test execution start - headless option
     */

    @Test
    public void testStartTestsHeadless() {
        testRunId = new TestRunServiceAPIImplV1().create();
        PostStartTestsInTestRunV1HeadlessMethod postStartTestsInTestRunV1Method = new PostStartTestsInTestRunV1HeadlessMethod(testRunId);
        apiExecutor.expectStatus(postStartTestsInTestRunV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postStartTestsInTestRunV1Method);
        apiExecutor.validateResponse(postStartTestsInTestRunV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testStartTestsHeadlessWithoutStartedAt() {
        testRunId = new TestRunServiceAPIImplV1().create();
        PostStartTestsInTestRunV1HeadlessMethod postStartTestsInTestRunV1Method = new PostStartTestsInTestRunV1HeadlessMethod(testRunId);
        postStartTestsInTestRunV1Method.removeProperty("startedAt");
        apiExecutor.expectStatus(postStartTestsInTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postStartTestsInTestRunV1Method);
    }

    @Test(dataProvider = "startedAtDataProvider")
    public void testStartTestsHeadlessWithStartedAt(String description, String startedAt) {
        testRunId = new TestRunServiceAPIImplV1().create();
        PostStartTestsInTestRunV1HeadlessMethod postStartTestsInTestRunV1Method = new PostStartTestsInTestRunV1HeadlessMethod(testRunId);
        postStartTestsInTestRunV1Method.addProperty("startedAt", startedAt);
        apiExecutor.expectStatus(postStartTestsInTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postStartTestsInTestRunV1Method);
    }

    /**
     * Test run execution finish
     */

    @Test
    public void testFinishTestRun() {
        testRunId = new TestRunServiceAPIImplV1().create();
        PutFinishTestRunV1Method putFinishTestRunV1Method = new PutFinishTestRunV1Method(testRunId, OffsetDateTime.now().toString());
        apiExecutor.expectStatus(putFinishTestRunV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putFinishTestRunV1Method);
    }

    @Test(dataProvider = "startedAtDataProvider", description = "negative")
    public void testFinishTestRunWithEndedAt(String description, String endedAt) {
        testRunId = new TestRunServiceAPIImplV1().create();
        PutFinishTestRunV1Method putFinishTestRunV1Method = new PutFinishTestRunV1Method(testRunId, OffsetDateTime.now().toString());
        putFinishTestRunV1Method.addProperty("endedAt", endedAt);
        apiExecutor.expectStatus(putFinishTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putFinishTestRunV1Method);
    }

    @Test(description = "negative")
    public void testFinishTestRunWithoutEndedAt() {
        testRunId = new TestRunServiceAPIImplV1().create();
        PutFinishTestRunV1Method putFinishTestRunV1Method = new PutFinishTestRunV1Method(testRunId, OffsetDateTime.now().toString());
        putFinishTestRunV1Method.removeProperty("endedAt");
        apiExecutor.expectStatus(putFinishTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putFinishTestRunV1Method);
    }

    /**
     * Test execution finish
     */

    @DataProvider(name = "testResult")
    public Object[][] getTestResult() {
        return new Object[][]{{"PASSED"}, {"FAILED"}, {"ABORTED"}, {"SKIPPED"}};
    }

    @DataProvider(name = "rqMandatoryFields")
    public Object[][] getRqMandatoryFieldsForUpdateTest() {
        return new Object[][]{{"result"}, {"endedAt"}};
    }

    @Test(dataProvider = "testResult")
    public void testFinishTestWithResult(String result) {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        PutFinishTestInTestRunV1Method putFinishTestInTestRunV1Method = new PutFinishTestInTestRunV1Method(testRunId, testId, result);
        apiExecutor.expectStatus(putFinishTestInTestRunV1Method, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(putFinishTestInTestRunV1Method);
        apiExecutor.validateResponse(putFinishTestInTestRunV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String actualResult = JsonPath.from(response).getString(JSONConstant.RESULT);
        Assert.assertEquals(actualResult, result, "Result is not as expected!");
    }

    @Test(enabled = false, dataProvider = "testResult", description = "testResultToLowerCase")
    public void testFinishTestWithResultToLowerCase(String result) {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        PutFinishTestInTestRunV1Method putFinishTestInTestRunV1Method = new PutFinishTestInTestRunV1Method(testRunId, testId, result.toLowerCase());
        apiExecutor.expectStatus(putFinishTestInTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putFinishTestInTestRunV1Method);
    }

    @Test(dataProvider = "rqMandatoryFields")
    public void testFinishTestWithoutField(String field) {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        PutFinishTestInTestRunV1Method putFinishTestInTestRunV1Method = new PutFinishTestInTestRunV1Method(testRunId, testId, RESULT_PASSED);
        putFinishTestInTestRunV1Method.removeProperty(field);
        apiExecutor.expectStatus(putFinishTestInTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putFinishTestInTestRunV1Method);
    }

    @Test(dataProvider = "startedAtDataProvider")
    public void testFinishTestWithEndedAt(String description, String endedAt) {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        PutFinishTestInTestRunV1Method putFinishTestInTestRunV1Method = new PutFinishTestInTestRunV1Method(testRunId, testId, RESULT_PASSED);
        putFinishTestInTestRunV1Method.addProperty("endedAt", endedAt);
        apiExecutor.expectStatus(putFinishTestInTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putFinishTestInTestRunV1Method);
    }

    /**
     * Test execution update - headless option
     */

    @DataProvider(name = "mandatoryFieldsUpdateTestsHeadless")
    public Object[][] getMandatoryFieldsUpdateTestsHeadless() {
        return new Object[][]{{"name"}, {"className"}, {"methodName"}};
    }

    @Test
    public void testUpdateTestsHeadless() {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTestHeadless(testRunId);
        PutUpdateTestsInTestRunV1HeadlessMethod putUpdateTestsInTestRunV1HeadlessMethod
                = new PutUpdateTestsInTestRunV1HeadlessMethod(testRunId, testId);
        apiExecutor.expectStatus(putUpdateTestsInTestRunV1HeadlessMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putUpdateTestsInTestRunV1HeadlessMethod);
        apiExecutor.validateResponse(putUpdateTestsInTestRunV1HeadlessMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test(dataProvider = "mandatoryFieldsUpdateTestsHeadless", description = "negative")
    public void testUpdateTestsHeadlessWithoutMandatory(String field) {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTestHeadless(testRunId);
        PutUpdateTestsInTestRunV1HeadlessMethod putUpdateTestsInTestRunV1HeadlessMethod
                = new PutUpdateTestsInTestRunV1HeadlessMethod(testRunId, testId);
        putUpdateTestsInTestRunV1HeadlessMethod.removeProperty(field);
        apiExecutor.expectStatus(putUpdateTestsInTestRunV1HeadlessMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putUpdateTestsInTestRunV1HeadlessMethod);
    }

    @Test(dataProvider = "mandatoryFieldsUpdateTestsHeadless", description = "negative")
    public void testUpdateTestsHeadlessWithEmptyMandatory(String field) {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTestHeadless(testRunId);
        PutUpdateTestsInTestRunV1HeadlessMethod putUpdateTestsInTestRunV1HeadlessMethod
                = new PutUpdateTestsInTestRunV1HeadlessMethod(testRunId, testId);
        putUpdateTestsInTestRunV1HeadlessMethod.addProperty(field, "");
        apiExecutor.expectStatus(putUpdateTestsInTestRunV1HeadlessMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putUpdateTestsInTestRunV1HeadlessMethod);
    }

    @Test
    public void testUpdateTestsHeadlessNonExistentTestRunId() {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTestHeadless(testRunId);
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
        PutUpdateTestsInTestRunV1HeadlessMethod putUpdateTestsInTestRunV1HeadlessMethod
                = new PutUpdateTestsInTestRunV1HeadlessMethod(testRunId, testId);
        apiExecutor.expectStatus(putUpdateTestsInTestRunV1HeadlessMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(putUpdateTestsInTestRunV1HeadlessMethod);
    }

    @Test
    public void testUpdateTestsHeadlessNonExistentTestId() {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTestHeadless(testRunId);
        new TestServiceAPIV1Impl().deleteTest(testRunId, testId);
        PutUpdateTestsInTestRunV1HeadlessMethod putUpdateTestsInTestRunV1HeadlessMethod
                = new PutUpdateTestsInTestRunV1HeadlessMethod(testRunId, testId);
        apiExecutor.expectStatus(putUpdateTestsInTestRunV1HeadlessMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(putUpdateTestsInTestRunV1HeadlessMethod);
    }

    @Test
    public void testUpdateTestsHeadlessWithInvalidTestRunId() {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTestHeadless(testRunId);
        PutUpdateTestsInTestRunV1HeadlessMethod putUpdateTestsInTestRunV1HeadlessMethod
                = new PutUpdateTestsInTestRunV1HeadlessMethod(testRunId, testId);
        String methodPath = putUpdateTestsInTestRunV1HeadlessMethod.getMethodPath().replace(String.valueOf(testRunId), "!");
        putUpdateTestsInTestRunV1HeadlessMethod.setMethodPath(methodPath);
        apiExecutor.expectStatus(putUpdateTestsInTestRunV1HeadlessMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putUpdateTestsInTestRunV1HeadlessMethod);
    }

    @Test
    public void testUpdateTestsHeadlessWithInvalidTestId() {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTestHeadless(testRunId);
        PutUpdateTestsInTestRunV1HeadlessMethod putUpdateTestsInTestRunV1HeadlessMethod
                = new PutUpdateTestsInTestRunV1HeadlessMethod(testRunId, testId);
        String methodPath = putUpdateTestsInTestRunV1HeadlessMethod.getMethodPath().replace(String.valueOf(testId), "!");
        putUpdateTestsInTestRunV1HeadlessMethod.setMethodPath(methodPath);
        apiExecutor.expectStatus(putUpdateTestsInTestRunV1HeadlessMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putUpdateTestsInTestRunV1HeadlessMethod);
    }

    @Test
    public void testUpdateTestsHeadlessWithHeadlessFalse() {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTestHeadless(testRunId);
        PutUpdateTestsInTestRunV1HeadlessMethod putUpdateTestsInTestRunV1HeadlessMethod
                = new PutUpdateTestsInTestRunV1HeadlessMethod(testRunId, testId);
        putUpdateTestsInTestRunV1HeadlessMethod.addUrlParameter("headless", String.valueOf(false));
        apiExecutor.expectStatus(putUpdateTestsInTestRunV1HeadlessMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putUpdateTestsInTestRunV1HeadlessMethod);
    }

    /**
     * Delete test in testRun
     */

    @Test
    public void testDeleteTest() {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId= new TestServiceAPIV1Impl().createTest(testRunId);
        DeleteTestByIdV1Method deleteTestByIdV1Method = new DeleteTestByIdV1Method(testRunId, testId);
        apiExecutor.expectStatus(deleteTestByIdV1Method, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteTestByIdV1Method);
    }

    @Test(enabled = false)
    public void testDeleteTestWithNonExistentTestId() {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId= new TestServiceAPIV1Impl().createTest(testRunId);
        new TestServiceAPIV1Impl().deleteTest(testRunId,testId);
        DeleteTestByIdV1Method deleteTestByIdV1Method = new DeleteTestByIdV1Method(testRunId, testId);
        apiExecutor.expectStatus(deleteTestByIdV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteTestByIdV1Method);
    }

    @Test(enabled = false)
    public void testDeleteTestWithNonExistentTestRunId() {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId= new TestServiceAPIV1Impl().createTest(testRunId);
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
        DeleteTestByIdV1Method deleteTestByIdV1Method = new DeleteTestByIdV1Method(testRunId, testId);
        apiExecutor.expectStatus(deleteTestByIdV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteTestByIdV1Method);
    }

    @Test
    public void testGetTestsByTestRunIdV1() {
        TestRunServiceAPIImplV1 testRunServiceAPIImplV1 = new TestRunServiceAPIImplV1();
        testRunId = testRunServiceAPIImplV1.create();
        String ciRunId = testRunServiceAPIImplV1.getCiRunId(testRunId);
        new TestServiceAPIV1Impl().createTest(testRunId);
        GetTestsByCiRunIdV1Method getTestsByCiRunIdV1Method = new GetTestsByCiRunIdV1Method(ciRunId);
        apiExecutor.expectStatus(getTestsByCiRunIdV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getTestsByCiRunIdV1Method);
        apiExecutor.validateResponse(getTestsByCiRunIdV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetTestRunStatusV1WithTestResultsFAILEDAndPASSED() {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId1 = new TestServiceAPIV1Impl().createTest(testRunId);
        int testId2 = new TestServiceAPIV1Impl().createTest(testRunId);
        new TestServiceAPIV1Impl().updateResultInTest(testRunId, testId1, RESULT_FAILED);
        new TestServiceAPIV1Impl().updateResultInTest(testRunId, testId2, RESULT_PASSED);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
        String actualResult = new TestRunServiceAPIImplV1().getTestRunResult(testRunId);
        Assert.assertEquals(actualResult, RESULT_FAILED, "Result is not as expected!");
    }

    @Test
    public void testGetTestRunStatusV1WithTestResultsPASSED() {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId1 = new TestServiceAPIV1Impl().createTest(testRunId);
        int testId2 = new TestServiceAPIV1Impl().createTest(testRunId);
        new TestServiceAPIV1Impl().updateResultInTest(testRunId, testId1, RESULT_PASSED);
        new TestServiceAPIV1Impl().updateResultInTest(testRunId, testId2, RESULT_PASSED);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
        String actualResult = new TestRunServiceAPIImplV1().getTestRunResult(testRunId);
        Assert.assertEquals(actualResult, RESULT_PASSED, "Result is not as expected!");
    }

    @Test
    public void testGetTestRunStatusV1WithTestResultsSKIPPEDAndPASSED() {
        testRunId = new TestRunServiceAPIImplV1().create();
        int testId1 = new TestServiceAPIV1Impl().createTest(testRunId);
        int testId2 = new TestServiceAPIV1Impl().createTest(testRunId);
        new TestServiceAPIV1Impl().updateResultInTest(testRunId, testId1, RESULT_SKIPPED);
        new TestServiceAPIV1Impl().updateResultInTest(testRunId, testId2, RESULT_PASSED);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
        String actualResult = new TestRunServiceAPIImplV1().getTestRunResult(testRunId);
        Assert.assertEquals(actualResult, RESULT_FAILED, "Result is not as expected!");
    }

    @Test
    public void testGetTestRunStatusV1WithUnfinishedProcess() {
        testRunId = new TestRunServiceAPIImplV1().create();
        new TestServiceAPIV1Impl().createTest(testRunId);
        String actualResult = new TestRunServiceAPIImplV1().getTestRunResult(testRunId);
        Assert.assertEquals(actualResult, RESULT_IN_PROGRESS, "Result is not as expected!");
    }

    @Test
    public void testGetTestsByCiRunIdV1WithTests() {
        testRunId = new TestRunServiceAPIImplV1().create();
        String ciRunId = new TestRunServiceAPIImplV1().getCiRunId(testRunId);
        new TestServiceAPIV1Impl().createTest(testRunId);
        GetTestsByCiRunIdV1Method getTestsByCiRunIdV1Method = new GetTestsByCiRunIdV1Method(ciRunId);
        apiExecutor.expectStatus(getTestsByCiRunIdV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getTestsByCiRunIdV1Method);
        apiExecutor.validateResponse(getTestsByCiRunIdV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test(enabled = false)
    public void testGetListTestRuns() {
        testRunId = new TestRunServiceAPIImplV1().create();
        new TestServiceAPIV1Impl().createTest(testRunId);
        GetListTestRunsV1Method getListTestRunsV1Method = new GetListTestRunsV1Method();
        apiExecutor.expectStatus(getListTestRunsV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getListTestRunsV1Method);
        apiExecutor.validateResponse(getListTestRunsV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        apiExecutor.validateResponseAgainstJSONSchema(getListTestRunsV1Method, "api/1.json");
    }

    @Test
    public void testDeleteTestRun() {
        testRunId = new TestRunServiceAPIImplV1().create();
        new TestServiceAPIV1Impl().createTest(testRunId);
        DeleteTestRunByIdV1Method deleteTestRunByIdV1Method = new DeleteTestRunByIdV1Method(testRunId);
        apiExecutor.expectStatus(deleteTestRunByIdV1Method, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteTestRunByIdV1Method);
    }

    @Test
    public void testMarkTestRunV1Reviewed() {
        testRunId = new TestRunServiceAPIImplV1().create();
        new TestRunServiceAPIImpl().finishTestRun(testRunId);
        PostMarkTestRunReviewedMethod postMarkTestRunReviewedMethod = new PostMarkTestRunReviewedMethod(testRunId);
        apiExecutor.expectStatus(postMarkTestRunReviewedMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postMarkTestRunReviewedMethod);
    }

    @Test
    public void testPostAIAnalysis() {
        testRunId = new TestRunServiceAPIImplV1().create();
        new TestServiceAPIV1Impl().createTest(testRunId);
        PostAIAnalysisMethod postAIAnalysisMethod = new PostAIAnalysisMethod(testRunId);
        apiExecutor.expectStatus(postAIAnalysisMethod, HTTPStatusCodeType.ACCEPTED);
        apiExecutor.callApiMethod(postAIAnalysisMethod);
    }
}
