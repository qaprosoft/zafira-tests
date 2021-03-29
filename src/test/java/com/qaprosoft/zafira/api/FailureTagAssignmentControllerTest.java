package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.failureTagAssignment.DeleteFailureTagAssignmentMethod;
import com.qaprosoft.zafira.api.failureTagAssignment.GetFailureTagAssignmentMethod;
import com.qaprosoft.zafira.api.failureTagAssignment.PatchFailureTagAssignmentMethod;
import com.qaprosoft.zafira.api.failureTagAssignment.PostFailureTagAssignmentMethod;
import com.qaprosoft.zafira.constant.ConstantName;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.constant.TestRailConstant;
import com.qaprosoft.zafira.dataProvider.FailureTagAssignmentDataProvider;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.enums.UserFeedback;
import com.qaprosoft.zafira.service.impl.FailureTagAssignmentServiceImpl;
import com.qaprosoft.zafira.service.impl.FailureTagServiceImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceV1Impl;
import com.zebrunner.agent.core.annotation.TestLabel;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class FailureTagAssignmentControllerTest extends ZafiraAPIBaseTest {
    private int testRunId;
    private int tagId;
    private static FailureTagAssignmentServiceImpl failureTagAssignmentService = new FailureTagAssignmentServiceImpl();
    private static FailureTagServiceImpl failureTagService = new FailureTagServiceImpl();
    private static String tagName="newTAG".concat(RandomStringUtils.randomAlphabetic(5));

    @AfterMethod
    public void testDeleteTestRun() {
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
        failureTagService.deleteFailureTag(tagId);
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40175")
    public void testGetFailureTagAssignment() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        tagId = failureTagService.createFailureTag(tagName);
        failureTagAssignmentService.assignFailureTag(testId, tagId);
        GetFailureTagAssignmentMethod getFailureTagsMethod = new GetFailureTagAssignmentMethod(testId);
        apiExecutor.expectStatus(getFailureTagsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getFailureTagsMethod);
        apiExecutor.validateResponse(getFailureTagsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int actualTagId = JsonPath.from(rs).getInt("items[0].tag.id") ;
        Assert.assertEquals(actualTagId,tagId, "TagId is not as expected!");
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40176")
    public void testGetFailureTagsAssignmentWithoutTagsInTest() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        GetFailureTagAssignmentMethod getFailureTagsMethod = new GetFailureTagAssignmentMethod(testId);
        apiExecutor.expectStatus(getFailureTagsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getFailureTagsMethod);
        Assert.assertNull(JsonPath.from(rs).get(JSONConstant.ITEMS_0));
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40177")
    public void testPostFailureTagAssignment() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        tagId = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        PostFailureTagAssignmentMethod postFailureTagsMethod = new PostFailureTagAssignmentMethod(testId, tagId);
        apiExecutor.expectStatus(postFailureTagsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postFailureTagsMethod);
        apiExecutor.validateResponse(postFailureTagsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int actualTag = failureTagAssignmentService.getFailureTag(testId);
        Assert.assertEquals(actualTag, tagId,"Tag is not as expected!");
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40178")
    public void testPostFailureTagAssignmentWithNonExistentTestId() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().deleteTest(testRunId,testId);
        tagId = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        PostFailureTagAssignmentMethod postFailureTagsMethod = new PostFailureTagAssignmentMethod(testId, tagId);
        apiExecutor.expectStatus(postFailureTagsMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(postFailureTagsMethod);
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40179")
    public void testPostFailureTagAssignmentWithNonExistentTagId() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        int tagId = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        failureTagService.deleteFailureTag(tagId);
        PostFailureTagAssignmentMethod postFailureTagsMethod = new PostFailureTagAssignmentMethod(testId, tagId);
        apiExecutor.expectStatus(postFailureTagsMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(postFailureTagsMethod);
    }

    @Test(description = "negative Now 500", enabled = false)
    public void testPostFailureTagAssignmentWithoutTestId() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        int tagIdCr = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        PostFailureTagAssignmentMethod postFailureTagsMethod = new PostFailureTagAssignmentMethod(testId, tagIdCr);
        postFailureTagsMethod.removeProperty(JSONConstant.TEST_ID);
        apiExecutor.expectStatus(postFailureTagsMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postFailureTagsMethod);
        failureTagService.deleteFailureTag(tagIdCr);
    }

    @Test(description = "negative Now 500", enabled = false)
    public void testPostFailureTagAssignmentWithoutTagId() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        int tagId = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        PostFailureTagAssignmentMethod postFailureTagsMethod = new PostFailureTagAssignmentMethod(testId, tagId);
        postFailureTagsMethod.removeProperty(JSONConstant.TAG_ID);
        apiExecutor.expectStatus(postFailureTagsMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postFailureTagsMethod);
        failureTagService.deleteFailureTag(tagId);
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40180")
    public void testDeleteFailureTagAssignment() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        tagId = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        int tagAssignmentId = failureTagAssignmentService.assignFailureTag(testId, tagId);
        DeleteFailureTagAssignmentMethod deleteFailureTagAssignmentMethod = new DeleteFailureTagAssignmentMethod(tagAssignmentId);
        apiExecutor.expectStatus(deleteFailureTagAssignmentMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteFailureTagAssignmentMethod);
        failureTagAssignmentService.getAllFailureTagAssignments(testId);
    }

    @Test()
    public void testDeleteFailureTagAssignmentWithNonExistingTagId() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        int tagIdCr = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        int tagId = failureTagAssignmentService.assignFailureTag(testId, tagIdCr);
        failureTagAssignmentService.deleteFailureTagAssignment(tagId);
        DeleteFailureTagAssignmentMethod deleteFailureTagAssignmentMethod = new DeleteFailureTagAssignmentMethod(tagId);
        apiExecutor.expectStatus(deleteFailureTagAssignmentMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteFailureTagAssignmentMethod);
        failureTagService.deleteFailureTag(tagIdCr);
    }

    @Test(enabled = false)
    public void testPatchFailureTagAssignment() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        int tagIdCr = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        int tagId = failureTagAssignmentService.assignFailureTag(testId, tagIdCr);
        String feedbackValue = String.valueOf(UserFeedback.LIKE);
        PatchFailureTagAssignmentMethod patchFailureTagAssignmentMethod = new PatchFailureTagAssignmentMethod(feedbackValue, tagId);
        apiExecutor.expectStatus(patchFailureTagAssignmentMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(patchFailureTagAssignmentMethod);
        failureTagService.deleteFailureTag(tagIdCr);
    }

    @Test(dataProvider =  "user-feedback",dataProviderClass = FailureTagAssignmentDataProvider.class)
    public void testPatchFailureTagAssignmentId1(String userFeedback) {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        // int tagIdCr = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        ///  int tagId =  failureTagAssignmentService.assignFailureTag(testId,tagIdCr);
        PatchFailureTagAssignmentMethod patchFailureTagAssignmentMethod = new PatchFailureTagAssignmentMethod(userFeedback, 1);
        apiExecutor.expectStatus(patchFailureTagAssignmentMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(patchFailureTagAssignmentMethod);
        //   failureTagService.deleteFailureTag(tagIdCr);
    }

    @Test(description = "negative Now 500", enabled = false)
    public void testPatchFailureTagAssignmentWithUnknownUserFeedBack() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        // int tagIdCr = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        ///  int tagId =  failureTagAssignmentService.assignFailureTag(testId,tagIdCr);
        String feedbackValue = String.valueOf(UserFeedback.LIKE)+"Unknown";
        PatchFailureTagAssignmentMethod patchFailureTagAssignmentMethod = new PatchFailureTagAssignmentMethod(feedbackValue, 1);
        apiExecutor.expectStatus(patchFailureTagAssignmentMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(patchFailureTagAssignmentMethod);
        //   failureTagService.deleteFailureTag(tagIdCr);
    }
}
