package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.failureTagAssignment.DeleteFailureTagsMethod;
import com.qaprosoft.zafira.api.failureTagAssignment.GetFailureTagsMethod;
import com.qaprosoft.zafira.api.failureTagAssignment.PostFailureTagsMethod;
import com.qaprosoft.zafira.constant.ConstantName;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.FailureTagAssignmentServiceImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceV1Impl;
import io.restassured.path.json.JsonPath;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class FailureTagAssignmentControllerTest extends ZafiraAPIBaseTest {
    private int testRunId;
    private static FailureTagAssignmentServiceImpl failureTagAssignmentService = new FailureTagAssignmentServiceImpl();

    @AfterMethod
    public void testDeleteTestRun() {
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
    }

    @Test()
    public void testGetFailureTags() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        failureTagAssignmentService.assignFailureTag(testId);
        GetFailureTagsMethod getFailureTagsMethod = new GetFailureTagsMethod(testId);
        apiExecutor.expectStatus(getFailureTagsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getFailureTagsMethod);
        apiExecutor.validateResponse(getFailureTagsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test()
    public void testGetFailureTagsWithoutTagsInTest() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        GetFailureTagsMethod getFailureTagsMethod = new GetFailureTagsMethod(testId);
        apiExecutor.expectStatus(getFailureTagsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getFailureTagsMethod);
        Assert.assertNull(JsonPath.from(rs).get("items[0]"));
    }

    @Test()
    public void testPostFailureTags() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        PostFailureTagsMethod postFailureTagsMethod = new PostFailureTagsMethod(testId);
        apiExecutor.expectStatus(postFailureTagsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postFailureTagsMethod);
        apiExecutor.validateResponse(postFailureTagsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test()
    public void testDeleteFailureTags() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        int tagId =  failureTagAssignmentService.assignFailureTag(testId);
        DeleteFailureTagsMethod deleteFailureTagsMethod = new DeleteFailureTagsMethod(tagId);
        apiExecutor.expectStatus(deleteFailureTagsMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteFailureTagsMethod);
    }

    @Test()
    public void testDeleteFailureTagsWithNonExistingTagId() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        int tagId =  failureTagAssignmentService.assignFailureTag(testId);
        failureTagAssignmentService.deleteFailureTag(tagId);
        DeleteFailureTagsMethod deleteFailureTagsMethod = new DeleteFailureTagsMethod(tagId);
        apiExecutor.expectStatus(deleteFailureTagsMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteFailureTagsMethod);
    }
}
