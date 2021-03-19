package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.failureTagAssignment.GetStacktraceLabelsMethod;
import com.qaprosoft.zafira.api.testController.*;
import com.qaprosoft.zafira.api.testController.v1.GetTestsV1Method;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.ConstantName;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.*;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class TestControllerTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = Logger.getLogger(TestControllerTest.class);
    private static final String EXISTING_ISSUE = "ZEB-1871";
    private static final String TEST_STATUS_FAILED = "FAILED";
    private static final String STACKTRACE_LABELS_NAME = "BUSINESS_ISSUE";

    @Test
    public void testStartTest() {
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        PostStartTestMethod postStartTestMethod = new PostStartTestMethod(testCaseId, testRunId);
        apiExecutor.expectStatus(postStartTestMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postStartTestMethod);
        apiExecutor.validateResponse(postStartTestMethod,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testFinishTest() {
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testId = new TestServiceImpl().create(testCaseId, testRunId);
        PostFinishTestMethod postFinishTestMethod = new PostFinishTestMethod(testCaseId, testRunId, testId);
        apiExecutor.expectStatus(postFinishTestMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postFinishTestMethod);
        apiExecutor.validateResponse(postFinishTestMethod,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testUpdateTestStatus() {
        String expectedTestStatusValue = R.TESTDATA.get(ConfigConstant.TEST_STATUS_EXPECTED_UPDATE_KEY);
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testId = new TestServiceImpl().create(testCaseId, testRunId);
        PutUpdateTestStatusMethod putUpdateTestStatusMethod = new PutUpdateTestStatusMethod(testId, testSuiteId,
                jobId, expectedTestStatusValue);
        apiExecutor.expectStatus(putUpdateTestStatusMethod, HTTPStatusCodeType.OK);
        String putTestRs = apiExecutor.callApiMethod(putUpdateTestStatusMethod);
        apiExecutor.validateResponse(putUpdateTestStatusMethod,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String testStatus = JsonPath.from(putTestRs).get(JSONConstant.STATUS_KEY);
        Assert.assertEquals(testStatus, expectedTestStatusValue, "Test status was not updated!");
    }

    @Test
    public void testCreateTestArtifact() {
        TestServiceImpl testServiceImpl = new TestServiceImpl();
        String link = R.TESTDATA.get(ConfigConstant.LINK_KEY);
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testId = testServiceImpl.create(testCaseId, testRunId);
        PostCreateTestArtifactMethod postCreateTestArtifactMethod = new PostCreateTestArtifactMethod(link, testId);
        apiExecutor.expectStatus(postCreateTestArtifactMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postCreateTestArtifactMethod);
        String testRs = testServiceImpl.getAllTest(testRunId);
        List<String> artifactsList = JsonPath.from(testRs).getList(JSONConstant.RESULT_ARTIFACT_KEY);
        LOGGER.info(String.format("Artifact ID: %s", artifactsList.toString()));
        Assert.assertTrue(artifactsList.toString().contains(link), "Test's artifact was not attached to test!");
    }

    @Test
    public void testDeleteTestById() {
        TestServiceImpl testService = new TestServiceImpl();
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testId = testService.create(testCaseId, testRunId);
        String testRs = testService.getAllTest(testRunId);
        int testIdRs = JsonPath.from(testRs).get(JSONConstant.ALL_ID_FROM_RESULTS_KEY);
        Assert.assertEquals(testId, testIdRs, "Test was not create!");
        DeleteTestByIdMethod deleteTestByIdMethod = new DeleteTestByIdMethod(testId);
        apiExecutor.expectStatus(deleteTestByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteTestByIdMethod);
        String testRsAfterDelete = testService.getAllTest(testRunId);
        Assert.assertFalse(testRsAfterDelete.contains(String.valueOf(testIdRs)), "Test was not deleted!");
    }

    @Test
    public void testLinkWorkItemToTest() {
        String expectedJiraIdValue = R.TESTDATA.get(ConfigConstant.EXPECTED_JIRA_ID_KEY);
        String workItemType = R.TESTDATA.get(ConfigConstant.WORK_ITEM_TYPE_KEY);
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testId = new TestServiceImpl().create(testCaseId, testRunId);
        new TestServiceImpl().updateTestStatus(testId, testSuiteId, jobId, ConstantName.FAILED);
        PostLinkWorkItemMethod postLinkWorkItemMethod = new PostLinkWorkItemMethod(testCaseId, expectedJiraIdValue,
                testId, workItemType);
        apiExecutor.expectStatus(postLinkWorkItemMethod, HTTPStatusCodeType.OK);
        String linkWorkItemRs = apiExecutor.callApiMethod(postLinkWorkItemMethod);
        String jiraId = JsonPath.from(linkWorkItemRs).get(JSONConstant.JIRA_ID_KEY);
        Assert.assertEquals(jiraId, expectedJiraIdValue, "Work item was not linked to test!");
    }

    @Test
    public void testLinkWorkItemToTestWithStatusPASSED() {
        String expectedJiraIdValue = R.TESTDATA.get(ConfigConstant.EXPECTED_JIRA_ID_KEY);
        String workItemType = R.TESTDATA.get(ConfigConstant.WORK_ITEM_TYPE_KEY);
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testId = new TestServiceImpl().create(testCaseId, testRunId);
        new TestServiceImpl().updateTestStatus(testId, testSuiteId, jobId, ConstantName.PASSED);
        PostLinkWorkItemMethod postLinkWorkItemMethod = new PostLinkWorkItemMethod(testCaseId, expectedJiraIdValue,
                testId, workItemType);
        apiExecutor.expectStatus(postLinkWorkItemMethod, HTTPStatusCodeType.FORBIDDEN);
    }

    @Test
    public void testGetWorkItem() {
        String workItemType = R.TESTDATA.get(ConfigConstant.WORK_ITEM_TYPE_KEY);
        String jiraId = R.TESTDATA.get(ConfigConstant.EXPECTED_JIRA_ID_KEY);
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testId = new TestServiceImpl().create(testCaseId, testRunId);
        new TestServiceImpl().updateTestStatus(testId, testSuiteId, jobId, ConstantName.FAILED);
        apiExecutor.callApiMethod(new PostLinkWorkItemMethod(testCaseId, jiraId, testId, workItemType));
        GetWorkItemMethod getWorkItemMethod = new GetWorkItemMethod(testId, workItemType);
        apiExecutor.expectStatus(getWorkItemMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getWorkItemMethod);
        apiExecutor.validateResponse(getWorkItemMethod,
                JSONCompareMode.LENIENT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCreateWorkItem() {
        String jiraId = R.TESTDATA.get(ConfigConstant.EXPECTED_JIRA_ID_KEY);
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testId = new TestServiceImpl().create(testCaseId, testRunId);
        PostCreateWorkItemMethod postCreateWorkItemMethod = new PostCreateWorkItemMethod(testId, jiraId);
        apiExecutor.expectStatus(postCreateWorkItemMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postCreateWorkItemMethod);
        apiExecutor.validateResponse(postCreateWorkItemMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testRetrieveTestBySearchCriteria() {
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        new TestServiceImpl().create(testCaseId, testRunId);
        PostRetrieveTestBySearchCriteriaMethod postRetrieveTestBySearchCriteriaMethod = new PostRetrieveTestBySearchCriteriaMethod(testRunId);
        apiExecutor.expectStatus(postRetrieveTestBySearchCriteriaMethod, HTTPStatusCodeType.OK);
        String testRs = apiExecutor.callApiMethod(postRetrieveTestBySearchCriteriaMethod);
        apiExecutor.validateResponse(postRetrieveTestBySearchCriteriaMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int allTestId = JsonPath.from(testRs).get(JSONConstant.ALL_ID_FROM_RESULTS_KEY);
        LOGGER.info(String.format("Test Ids: %s", allTestId));
    }

    @Test
    public void testDeleteWorkItem() {
        String expectedJiraIdValue = R.TESTDATA.get(ConfigConstant.EXPECTED_JIRA_ID_KEY);
        String workItemType = R.TESTDATA.get(ConfigConstant.WORK_ITEM_TYPE_KEY);
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        TestServiceImpl testServiсeImpl = new TestServiceImpl();
        int testId = testServiсeImpl.create(testCaseId, testRunId);
        new TestServiceImpl().updateTestStatus(testId, testSuiteId, jobId, ConstantName.FAILED);
        String linkWorkItemRs = apiExecutor.callApiMethod(new PostLinkWorkItemMethod(testCaseId, expectedJiraIdValue,
                testId, workItemType));
        int workItemId = JsonPath.from(linkWorkItemRs).get(JSONConstant.ID_KEY);
        String testRs = testServiсeImpl.getAllTest(testRunId);
        int workItemIdRs = JsonPath.from(testRs).get(JSONConstant.WORK_ITEM_ID_CHECK_KEY);
        Assert.assertNotEquals(0, workItemIdRs, "Work item was not linked!");
        apiExecutor.callApiMethod(new DeleteWorkItemMethod(testId, workItemId));
        String testRsAfterDelete = testServiсeImpl.getAllTest(testRunId);
        List<Integer> workItemsAfterDelete = JsonPath.from(testRsAfterDelete).getList(JSONConstant.WORK_ITEMS_ARRAY_KEY);
        Assert.assertTrue(workItemsAfterDelete.isEmpty(), "Work item was not deleted!");
    }

    @Test(enabled = false)
    public void testGetsAvailableStacktraceLabels() {
        GetStacktraceLabelsMethod getStacktraceLabelsMethod = new GetStacktraceLabelsMethod();
        String rs = apiExecutor.callApiMethod(getStacktraceLabelsMethod);
        apiExecutor.expectStatus(getStacktraceLabelsMethod, HTTPStatusCodeType.OK);
        List<String> listOfStacktraceLabels = JsonPath.from(rs).get();
        Assert.assertFalse(listOfStacktraceLabels.isEmpty());
    }

    @Test
    public void testGetJiraIssueByItsId() {
        GetJiraIssueByItsIdMethod getJiraIssueByItsIdMethod = new GetJiraIssueByItsIdMethod(EXISTING_ISSUE);
        apiExecutor.callApiMethod(getJiraIssueByItsIdMethod);
        apiExecutor.expectStatus(getJiraIssueByItsIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.validateResponse(getJiraIssueByItsIdMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetTestResultsHistoryById() {
        TestServiceImpl testServiceImpl = new TestServiceImpl();
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testId = testServiceImpl.create(testCaseId, testRunId);
        GetTestResultsHistoryByIdMethod getTestResultsHistoryByIdMethod = new GetTestResultsHistoryByIdMethod(testId);
        apiExecutor.callApiMethod(getTestResultsHistoryByIdMethod);
        apiExecutor.expectStatus(getTestResultsHistoryByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.validateResponse(getTestResultsHistoryByIdMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testUpdatesTestWorkItemWithTestStatusFailed() {
        String expectedJiraId = EXISTING_ISSUE;
        String workItemType = R.TESTDATA.get(ConfigConstant.WORK_ITEM_TYPE_KEY);
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testId = new TestServiceImpl().create(testCaseId, testRunId);
        new TestServiceImpl().updateTestStatus(testId, testSuiteId,
                jobId, TEST_STATUS_FAILED);
        PutWorkItemMethod postLinkWorkItemMethod = new PutWorkItemMethod(testCaseId, expectedJiraId,
                testId, workItemType);
        apiExecutor.expectStatus(postLinkWorkItemMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postLinkWorkItemMethod);
        apiExecutor.validateResponse(postLinkWorkItemMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String jiraId = JsonPath.from(rs).get(JSONConstant.JIRA_ID_KEY);
        Assert.assertEquals(jiraId, jiraId, "Work item was not updated in test!");
    }

    @Test
    public void testUpdateBatchPatchesOfTestStatus() {
        String expectedTestStatusValue = R.TESTDATA.get(ConfigConstant.TEST_STATUS_EXPECTED_UPDATE_KEY);
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testId = new TestServiceImpl().create(testCaseId, testRunId);
        PatchUpdateBatchPatchesOfTestStatusMethod patchUpdateBatchPatchesOfTestStatusMethod
                = new PatchUpdateBatchPatchesOfTestStatusMethod(testId, testRunId, expectedTestStatusValue);
        apiExecutor.expectStatus(patchUpdateBatchPatchesOfTestStatusMethod, HTTPStatusCodeType.OK);
        String putTestRs = apiExecutor.callApiMethod(patchUpdateBatchPatchesOfTestStatusMethod);
        apiExecutor.validateResponse(patchUpdateBatchPatchesOfTestStatusMethod,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String testStatus = JsonPath.from(putTestRs).get(JSONConstant.ARRAY_STATUS_KEY);
        Assert.assertEquals(testStatus, expectedTestStatusValue, "Test status was not updated!");
    }

    @Test(enabled = false)
    public void testGetTests() {
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testId = new TestServiceImpl().create(testCaseId, testRunId);
        GetTestsV1Method getTestsV1Method = new GetTestsV1Method(testRunId);
        apiExecutor.expectStatus(getTestsV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getTestsV1Method);
        apiExecutor.validateResponse(getTestsV1Method,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int actualId = JsonPath.from(rs).getInt(JSONConstant.ITEMS_TEST_ID_KEY);
        Assert.assertEquals(testId, actualId, "Tests was not got!");
    }

    @Test(enabled = false)
    public void testPutStacktraceLabel() {
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testId = new TestServiceImpl().create(testCaseId, testRunId);
        new TestServiceImpl().updateTestStatus(testId, testSuiteId,
                jobId, TEST_STATUS_FAILED);
        new TestRunServiceAPIImpl().createAIAnalysis(testRunId);
        PutStacktraceLabelsMethod putStacktraceLabelsMethod = new PutStacktraceLabelsMethod(testId, STACKTRACE_LABELS_NAME);
        apiExecutor.expectStatus(putStacktraceLabelsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putStacktraceLabelsMethod);
        apiExecutor.validateResponse(putStacktraceLabelsMethod,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test(enabled = false)
    public void testDeleteStacktraceLabel() {
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        int testId = new TestServiceImpl().create(testCaseId, testRunId);
        new TestServiceImpl().updateTestStatus(testId, testSuiteId,
                jobId, TEST_STATUS_FAILED);
        new TestRunServiceAPIImpl().createAIAnalysis(testRunId);
        new TestServiceImpl().updateTestStacktraceLabel(testId,
                STACKTRACE_LABELS_NAME);
        DeleteStacktraceLabelsMethod deleteStacktraceLabelsMethod = new DeleteStacktraceLabelsMethod(testId);
        apiExecutor.expectStatus(deleteStacktraceLabelsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteStacktraceLabelsMethod);
    }
}
