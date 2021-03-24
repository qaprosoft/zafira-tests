package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.failureTagAssignment.DeleteFailureTagAssignmentMethod;
import com.qaprosoft.zafira.api.failureTagAssignment.GetFailureTagAssignmentMethod;
import com.qaprosoft.zafira.api.failureTagAssignment.PatchFailureTagAssignmentMethod;
import com.qaprosoft.zafira.api.failureTagAssignment.PostFailureTagAssignmentMethod;
import com.qaprosoft.zafira.constant.ConstantName;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.FailureTagAssignmentServiceImpl;
import com.qaprosoft.zafira.service.impl.FailureTagServiceImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceV1Impl;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class FailureTagAssignmentControllerTest extends ZafiraAPIBaseTest {
    private int testRunId;
    private static FailureTagAssignmentServiceImpl failureTagAssignmentService = new FailureTagAssignmentServiceImpl();
    private static FailureTagServiceImpl failureTagService = new FailureTagServiceImpl();

    @AfterMethod
    public void testDeleteTestRun() {
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
    }

    @Test()
    public void testGetFailureTagAssignment() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        int tagIdCr = failureTagService.createFailureTag("newTAG");
        failureTagAssignmentService.assignFailureTag(testId,tagIdCr);
        GetFailureTagAssignmentMethod getFailureTagsMethod = new GetFailureTagAssignmentMethod(testId);
        apiExecutor.expectStatus(getFailureTagsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getFailureTagsMethod);
        apiExecutor.validateResponse(getFailureTagsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        failureTagService.deleteFailureTag(tagIdCr);
    }

    @Test()
    public void testGetFailureTagsAssignmentWithoutTagsInTest() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        GetFailureTagAssignmentMethod getFailureTagsMethod = new GetFailureTagAssignmentMethod(testId);
        apiExecutor.expectStatus(getFailureTagsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getFailureTagsMethod);
        Assert.assertNull(JsonPath.from(rs).get("items[0]"));
    }

    @Test()
    public void testPostFailureTagAssignment() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        int tagId = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        PostFailureTagAssignmentMethod postFailureTagsMethod = new PostFailureTagAssignmentMethod(testId,tagId);
        apiExecutor.expectStatus(postFailureTagsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postFailureTagsMethod);
        apiExecutor.validateResponse(postFailureTagsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        failureTagService.deleteFailureTag(tagId);
    }

    @Test(description = "negative",enabled = false)
    public void testPostFailureTagAssignmentWithoutTestId() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        int tagIdCr = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        PostFailureTagAssignmentMethod postFailureTagsMethod = new PostFailureTagAssignmentMethod(testId,tagIdCr);
        postFailureTagsMethod.removeProperty(JSONConstant.TEST_ID);
        apiExecutor.expectStatus(postFailureTagsMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postFailureTagsMethod);
        failureTagService.deleteFailureTag(tagIdCr);
    }

    @Test(description = "negative",enabled = false)
    public void testPostFailureTagAssignmentWithoutTagId() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        int tagId = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        PostFailureTagAssignmentMethod postFailureTagsMethod = new PostFailureTagAssignmentMethod(testId,tagId);
        postFailureTagsMethod.removeProperty(JSONConstant.TAG_ID);
        apiExecutor.expectStatus(postFailureTagsMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postFailureTagsMethod);
        failureTagService.deleteFailureTag(tagId);
    }

    @Test()
    public void testDeleteFailureTagAssignment() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        int tagIdCr = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        int tagId =  failureTagAssignmentService.assignFailureTag(testId,tagIdCr);
        DeleteFailureTagAssignmentMethod deleteFailureTagAssignmentMethod = new DeleteFailureTagAssignmentMethod(tagId);
        apiExecutor.expectStatus(deleteFailureTagAssignmentMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteFailureTagAssignmentMethod);
        failureTagService.deleteFailureTag(tagId);
    }

    @Test()
    public void testDeleteFailureTagAssignmentWithNonExistingTagId() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        int tagIdCr = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        int tagId =  failureTagAssignmentService.assignFailureTag(testId,tagIdCr);
        failureTagAssignmentService.deleteFailureTagAssignment(tagId);
        DeleteFailureTagAssignmentMethod deleteFailureTagAssignmentMethod = new DeleteFailureTagAssignmentMethod(tagId);
        apiExecutor.expectStatus(deleteFailureTagAssignmentMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteFailureTagAssignmentMethod);
        failureTagService.deleteFailureTag(tagIdCr);
    }

    @Test()
    public void testPatchFailureTagAssignment() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        int tagIdCr = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        int tagId =  failureTagAssignmentService.assignFailureTag(testId,tagIdCr);
        String feedbackValue = "LIKE";
        PatchFailureTagAssignmentMethod patchFailureTagAssignmentMethod = new PatchFailureTagAssignmentMethod(feedbackValue,tagId);
        apiExecutor.expectStatus(patchFailureTagAssignmentMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(patchFailureTagAssignmentMethod);
        failureTagService.deleteFailureTag(tagIdCr);
    }

    @Test()
    public void testPatchFailureTagAssignmentId1() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
       // int tagIdCr = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
     ///  int tagId =  failureTagAssignmentService.assignFailureTag(testId,tagIdCr);
        String feedbackValue = "LIKE";
        PatchFailureTagAssignmentMethod patchFailureTagAssignmentMethod = new PatchFailureTagAssignmentMethod(feedbackValue,4);
        apiExecutor.expectStatus(patchFailureTagAssignmentMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(patchFailureTagAssignmentMethod);
     //   failureTagService.deleteFailureTag(tagIdCr);
    }
}
