package com.qaprosoft.zafira.api;


import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.testRunController.v1.PostStartTestsInTestRunV1HeadlessMethod;
import com.qaprosoft.zafira.api.testRunController.v1.PutUpdateTestsInTestRunV1HeadlessMethod;
import com.qaprosoft.zafira.constant.ConstantName;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceAPIV1Impl;
import com.qaprosoft.zafira.service.impl.TestServiceImpl;
import io.restassured.path.json.JsonPath;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.OffsetDateTime;

public class TestHeadlessOptionV1Test extends ZafiraAPIBaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestHeadlessOptionV1Test.class);
    private int testRunId;

    @AfterMethod
    public void deleteTestRun() {
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
    }

    /**
     * Test execution start - headless option
     */

    @DataProvider(name = "startedAtDataProvider")
    public Object[][] getStartedAt() {
        return new Object[][]{{"invalid", "!"},
                {"in future", OffsetDateTime.now().plusDays(5).toString()}};
    }

    @Test
    public void testStartTestsHeadless() {
        testRunId = new TestRunServiceAPIImplV1().create();
        PostStartTestsInTestRunV1HeadlessMethod postStartTestsInTestRunV1Method
                = new PostStartTestsInTestRunV1HeadlessMethod(testRunId);
        apiExecutor.expectStatus(postStartTestsInTestRunV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postStartTestsInTestRunV1Method);
        int testId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        apiExecutor.validateResponse(postStartTestsInTestRunV1Method,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertTrue(new TestServiceImpl().getAllTestIdsByTestRunId(testRunId).contains(testId),
                "Test with id " + testId + " was not found!");
    }

    @Test
    public void testStartTestsHeadlessWithEmptyName() {
        testRunId = new TestRunServiceAPIImplV1().create();
        PostStartTestsInTestRunV1HeadlessMethod postStartTestsInTestRunV1Method
                = new PostStartTestsInTestRunV1HeadlessMethod(testRunId);
        postStartTestsInTestRunV1Method.addProperty(JSONConstant.NAME, ConstantName.EMPTY);
        apiExecutor.expectStatus(postStartTestsInTestRunV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postStartTestsInTestRunV1Method);
        String name = JsonPath.from(rs).getString(JSONConstant.NAME);
        int testId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        apiExecutor.validateResponse(postStartTestsInTestRunV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertTrue(new TestServiceImpl().getAllTestIdsByTestRunId(testRunId).contains(testId),
                "Test with id " + testId + " was not found!");
        Assert.assertEquals(name, ConstantName.SYSTEM, "Name is incorrect!");
    }

    @Test
    public void testStartTestsHeadlessWithoutStartedAt() {
        testRunId = new TestRunServiceAPIImplV1().create();
        PostStartTestsInTestRunV1HeadlessMethod postStartTestsInTestRunV1Method
                = new PostStartTestsInTestRunV1HeadlessMethod(testRunId);
        postStartTestsInTestRunV1Method.removeProperty(JSONConstant.STARTED_AT);
        apiExecutor.expectStatus(postStartTestsInTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postStartTestsInTestRunV1Method);
    }

    @Test(dataProvider = "startedAtDataProvider")
    public void testStartTestsHeadlessWithStartedAt(String description, String startedAt) {
        testRunId = new TestRunServiceAPIImplV1().create();
        PostStartTestsInTestRunV1HeadlessMethod postStartTestsInTestRunV1Method
                = new PostStartTestsInTestRunV1HeadlessMethod(testRunId);
        postStartTestsInTestRunV1Method.addProperty(JSONConstant.STARTED_AT, startedAt);
        apiExecutor.expectStatus(postStartTestsInTestRunV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postStartTestsInTestRunV1Method);
    }

    /**
     * Test execution update - headless option
     */

    @DataProvider(name = "mandatoryFieldsUpdateTestsHeadless")
    public Object[][] getMandatoryFieldsUpdateTestsHeadless() {
        return new Object[][]{{JSONConstant.NAME}, {JSONConstant.CLASS_NAME}, {JSONConstant.METHOD_NAME}};
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
        putUpdateTestsInTestRunV1HeadlessMethod.addProperty(field, ConstantName.EMPTY);
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

    @Test
    public void testDeleteTestHeadless() {
         testRunId = new TestRunServiceAPIImplV1().create();
        int testId = new TestServiceAPIV1Impl().createTestHeadless(testRunId);
        new TestServiceAPIV1Impl().deleteTest(testRunId,testId);
        Assert.assertFalse(new TestServiceImpl().getAllTestIdsByTestRunId(testRunId).contains(testId),
                "Test with id " + testId + " was not found!");
    }
}
