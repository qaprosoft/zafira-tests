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

import java.util.List;

public class FailureTagAssignmentControllerTest extends ZafiraAPIBaseTest {
    private int testRunId;
    private int tagId;
    private static FailureTagAssignmentServiceImpl failureTagAssignmentService = new FailureTagAssignmentServiceImpl();
    private static FailureTagServiceImpl failureTagService = new FailureTagServiceImpl();
    private static String tagName = "newTAG".concat(RandomStringUtils.randomAlphabetic(5));

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
        int tagAssignment = failureTagAssignmentService.assignFailureTag(testId, tagId);

        GetFailureTagAssignmentMethod getFailureTagsMethod = new GetFailureTagAssignmentMethod(testId);
        apiExecutor.expectStatus(getFailureTagsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getFailureTagsMethod);
        apiExecutor.validateResponse(getFailureTagsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int actualTagId = JsonPath.from(rs).getInt("items[0].tag.id");

        Assert.assertEquals(actualTagId, tagId, "TagId is not as expected!");
        Assert.assertTrue(failureTagAssignmentService.getAllFailureTagAssignments(testId).contains(tagAssignment));
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

        int actualTagId = failureTagAssignmentService.getFailureTag(testId);
        Assert.assertEquals(actualTagId, tagId, "Tag is not as expected!");
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40178")
    public void testPostFailureTagAssignmentWithNonExistentTestId() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().deleteTest(testRunId, testId);
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

    @Test()
    public void testPostFailureTagAssignmentWithoutTestId() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        tagId = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));

        PostFailureTagAssignmentMethod postFailureTagsMethod = new PostFailureTagAssignmentMethod(testId, tagId);
        postFailureTagsMethod.removeProperty(JSONConstant.TEST_ID);
        apiExecutor.expectStatus(postFailureTagsMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postFailureTagsMethod);
    }

    @Test()
    public void testPostFailureTagAssignmentWithoutTagId() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        tagId = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));

        PostFailureTagAssignmentMethod postFailureTagsMethod = new PostFailureTagAssignmentMethod(testId, tagId);
        postFailureTagsMethod.removeProperty(JSONConstant.TAG_ID);
        apiExecutor.expectStatus(postFailureTagsMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postFailureTagsMethod);
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

        List<Integer> listAllFailureTagAssignments = failureTagAssignmentService.getAllFailureTagAssignments(testId);
        Assert.assertFalse(listAllFailureTagAssignments.contains(tagAssignmentId), "Tag assignment was not deleted!");
    }

    @Test()
    public void testDeleteFailureTagAssignmentWithNonExistingTagId() {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        tagId = failureTagService.createFailureTag("newTAG".concat(RandomStringUtils.randomAlphabetic(5)));
        int tagAssignmentId = failureTagAssignmentService.assignFailureTag(testId, tagId);
        failureTagAssignmentService.deleteFailureTagAssignment(tagAssignmentId);

        DeleteFailureTagAssignmentMethod deleteFailureTagAssignmentMethod = new DeleteFailureTagAssignmentMethod(tagAssignmentId);
        apiExecutor.expectStatus(deleteFailureTagAssignmentMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteFailureTagAssignmentMethod);
    }


    @Test(dataProvider = "user-feedback", dataProviderClass = FailureTagAssignmentDataProvider.class)
    public void testPatchFailureTagAssignment(String userFeedback) {
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        new TestRunServiceAPIImplV1().postAiAnalyze(testRunId);
        List<Integer> listTagAssignmentIds = failureTagAssignmentService.getAllFailureTagAssignments(testId);

        PatchFailureTagAssignmentMethod patchFailureTagAssignmentMethod =
                new PatchFailureTagAssignmentMethod(userFeedback, listTagAssignmentIds.get(0));
        apiExecutor.expectStatus(patchFailureTagAssignmentMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(patchFailureTagAssignmentMethod);
    }

    @Test(description = "negative Now 500", enabled = false)
    public void testPatchFailureTagAssignmentWithUnknownUserFeedBack() {
        String feedbackValue = UserFeedback.LIKE + "Unknown";
        testRunId = startTestRunV1();
        int testId = startTestV1(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, ConstantName.FAILED);
        new TestRunServiceAPIImplV1().postAiAnalyze(testRunId);
        List<Integer> listTagAssignmentIds = failureTagAssignmentService.getAllFailureTagAssignments(testId);

        PatchFailureTagAssignmentMethod patchFailureTagAssignmentMethod =
                new PatchFailureTagAssignmentMethod(feedbackValue, listTagAssignmentIds.get(0));
        apiExecutor.expectStatus(patchFailureTagAssignmentMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(patchFailureTagAssignmentMethod);
    }
}
