package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.testRunController.v1.*;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceAPIV1Impl;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

/**
 * Test Run Controller v1
 */

public class TestRunV1Test extends ZafiraAPIBaseTest {

    int testRunId;
    private static final String RESULT_SKIPPED = "SKIPPED";
    private static final String RESULT_FAILED = "FAILED";
    private static final String RESULT_PASSED = "PASSED";
    private static final String RESULT_IN_PROGRESS = "IN_PROGRESS";

    @AfterMethod
    public void testFinishTestRun() {
        new TestRunServiceAPIImpl().deleteById(testRunId);
    }

    @Test()
    public void testStartTestRunV1WithoutTests() {

        PostStartTestRunV1Method postStartTestRunV1Method = new PostStartTestRunV1Method();
        apiExecutor.expectStatus(postStartTestRunV1Method, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postStartTestRunV1Method);
        apiExecutor.validateResponse(postStartTestRunV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testRunId = JsonPath.from(response).getInt(JSONConstant.ID_KEY);

    }

    @Test
    public void testGetTestsByCiRunIdV1() {
        int testRunId = new TestRunServiceAPIImplV1().create();
        String ciRunId = new TestRunServiceAPIImplV1().getCiRunId(testRunId);
        GetTestsByCiRunIdV1Method getTestsByCiRunIdV1Method = new GetTestsByCiRunIdV1Method(ciRunId);
        apiExecutor.expectStatus(getTestsByCiRunIdV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getTestsByCiRunIdV1Method);
    }

    @Test
    public void testFinishTests() {
        int testRunId = new TestRunServiceAPIImplV1().create();
        PutFinishTestRunV1Method putFinishTestRunV1Method = new PutFinishTestRunV1Method(testRunId);
        apiExecutor.expectStatus(putFinishTestRunV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putFinishTestRunV1Method);
        new TestRunServiceAPIImpl().deleteById(testRunId);
    }

    @Test
    public void testStartTestsInTestRunV1() {
        int testRunId = new TestRunServiceAPIImplV1().create();
        PostStartTestsInTestRunV1Method postStartTestsInTestRunV1Method = new PostStartTestsInTestRunV1Method(testRunId);
        apiExecutor.expectStatus(postStartTestsInTestRunV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postStartTestsInTestRunV1Method);
        apiExecutor.validateResponse(postStartTestsInTestRunV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        new TestRunServiceAPIImpl().deleteById(testRunId);
    }

    @Test
    public void testGetTestsByCiRunIdV1WithTests() {
        int testRunId = new TestRunServiceAPIImplV1().create();
        String ciRunId = new TestRunServiceAPIImplV1().getCiRunId(testRunId);
        new TestServiceAPIV1Impl().createTest(testRunId);
        GetTestsByCiRunIdV1Method getTestsByCiRunIdV1Method = new GetTestsByCiRunIdV1Method(ciRunId);
        apiExecutor.expectStatus(getTestsByCiRunIdV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getTestsByCiRunIdV1Method);
        apiExecutor.validateResponse(getTestsByCiRunIdV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        new TestRunServiceAPIImpl().deleteById(testRunId);
    }

    @Test
    public void testUpdateTestWithResultPassedInTestRunV1() {
        int testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        PutUpdateTestInTestRunV1Method putUpdateTestInTestRunV1Method = new PutUpdateTestInTestRunV1Method(testRunId, testId, RESULT_PASSED);
        apiExecutor.expectStatus(putUpdateTestInTestRunV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putUpdateTestInTestRunV1Method);
        apiExecutor.validateResponse(putUpdateTestInTestRunV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String response = apiExecutor.callApiMethod(putUpdateTestInTestRunV1Method);
        String actualResult = JsonPath.from(response).getString(JSONConstant.RESULT);
        Assert.assertEquals(actualResult, RESULT_PASSED, "Result is not as expected!");
        new TestRunServiceAPIImpl().deleteById(testRunId);
    }

    @Test
    public void testUpdateTestWithResultFailedInTestRunV1() {
        int testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        PutUpdateTestInTestRunV1Method putUpdateTestInTestRunV1Method = new PutUpdateTestInTestRunV1Method(testRunId, testId, RESULT_FAILED);
        apiExecutor.expectStatus(putUpdateTestInTestRunV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putUpdateTestInTestRunV1Method);
        apiExecutor.validateResponse(putUpdateTestInTestRunV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String response = apiExecutor.callApiMethod(putUpdateTestInTestRunV1Method);
        String actualResult = JsonPath.from(response).getString(JSONConstant.RESULT);
        Assert.assertEquals(actualResult, RESULT_FAILED, "Result is not as expected!");
        new TestRunServiceAPIImpl().deleteById(testRunId);
    }

    @Test
    public void testUpdateTestWithResultSkippedInTestRunV1() {
        int testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTest(testRunId);
        PutUpdateTestInTestRunV1Method putUpdateTestInTestRunV1Method = new PutUpdateTestInTestRunV1Method(testRunId, testId, RESULT_SKIPPED);
        apiExecutor.expectStatus(putUpdateTestInTestRunV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putUpdateTestInTestRunV1Method);
        apiExecutor.validateResponse(putUpdateTestInTestRunV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String response = apiExecutor.callApiMethod(putUpdateTestInTestRunV1Method);
        String actualResult = JsonPath.from(response).getString(JSONConstant.RESULT);
        Assert.assertEquals(actualResult, RESULT_SKIPPED, "Result is not as expected!");
        new TestRunServiceAPIImpl().deleteById(testRunId);
    }

    @Test
    public void testGetTestRunStatusV1WithTestResultsFAILEDAndPASSED() {
        int testRunId = new TestRunServiceAPIImplV1().create();
        int testId1 = new TestServiceAPIV1Impl().createTest(testRunId);
        int testId2 = new TestServiceAPIV1Impl().createTest(testRunId);
        new TestServiceAPIV1Impl().updateResultInTest(testRunId, testId1, RESULT_FAILED);
        new TestServiceAPIV1Impl().updateResultInTest(testRunId, testId2, RESULT_PASSED);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
        String actualResult = new TestRunServiceAPIImplV1().getTestRunResult(testRunId);
        Assert.assertEquals(actualResult, RESULT_FAILED, "Result is not as expected!");
        new TestRunServiceAPIImpl().deleteById(testRunId);
    }

    @Test
    public void testGetTestRunStatusV1WithTestResultsPASSED() {
        int testRunId = new TestRunServiceAPIImplV1().create();
        int testId1 = new TestServiceAPIV1Impl().createTest(testRunId);
        int testId2 = new TestServiceAPIV1Impl().createTest(testRunId);
        new TestServiceAPIV1Impl().updateResultInTest(testRunId, testId1, RESULT_PASSED);
        new TestServiceAPIV1Impl().updateResultInTest(testRunId, testId2, RESULT_PASSED);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
        String actualResult = new TestRunServiceAPIImplV1().getTestRunResult(testRunId);
        Assert.assertEquals(actualResult, RESULT_PASSED, "Result is not as expected!");
        new TestRunServiceAPIImpl().deleteById(testRunId);
    }

    @Test
    public void testGetTestRunStatusV1WithTestResultsSKIPPEDAndPASSED() {
        int testRunId = new TestRunServiceAPIImplV1().create();
        int testId1 = new TestServiceAPIV1Impl().createTest(testRunId);
        int testId2 = new TestServiceAPIV1Impl().createTest(testRunId);
        new TestServiceAPIV1Impl().updateResultInTest(testRunId, testId1, RESULT_SKIPPED);
        new TestServiceAPIV1Impl().updateResultInTest(testRunId, testId2, RESULT_PASSED);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
        String actualResult = new TestRunServiceAPIImplV1().getTestRunResult(testRunId);
        Assert.assertEquals(actualResult, RESULT_FAILED, "Result is not as expected!");
        new TestRunServiceAPIImpl().deleteById(testRunId);
    }

    @Test
    public void testGetTestRunStatusV1WithUnfinishedProcess() {
        int testRunId = new TestRunServiceAPIImplV1().create();
        int testId1 = new TestServiceAPIV1Impl().createTest(testRunId);
        String actualResult = new TestRunServiceAPIImplV1().getTestRunResult(testRunId);
        Assert.assertEquals(actualResult, RESULT_IN_PROGRESS, "Result is not as expected!");
        new TestRunServiceAPIImpl().deleteById(testRunId);
    }
}
