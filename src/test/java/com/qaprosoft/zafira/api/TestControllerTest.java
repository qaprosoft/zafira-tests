package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.service.impl.*;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class TestControllerTest extends ZariraAPIBaseTest {
    private static final Logger LOGGER = Logger.getLogger(TestControllerTest.class);

    @Test
    public void testStartTest() {
        String token = new APIContextManager().getAccessToken();
        int testSuiteId = new TestSuiteServiceImpl().getId(token);
        int jobId = new JobServiceImpl().getId(token);
        int testCaseId = new TestCaseServiceImpl().getId(token, testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().getId(token, testSuiteId, jobId);
        apiExecutor.callApiMethod(new PostStartTestMethod(token, testCaseId, testRunId), HTTPStatusCodeType.OK, true,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testFinishTest() {
        String token = new APIContextManager().getAccessToken();
        int testSuiteId = new TestSuiteServiceImpl().getId(token);
        int jobId = new JobServiceImpl().getId(token);
        int testCaseId = new TestCaseServiceImpl().getId(token, testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().getId(token, testSuiteId, jobId);
        int testId = new TestServiceImpl().getId(token, testCaseId, testRunId);
        apiExecutor.callApiMethod(new PostFinishTestMethod(token, testCaseId, testRunId, testId), HTTPStatusCodeType.OK,
                true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testUpdateTestStatus() {
        String expectedTestStatusValue = R.TESTDATA.get(ConfigConstant.TEST_STATUS_EXPECTED_UPDATE_KEY);
        String token = new APIContextManager().getAccessToken();
        int testSuiteId = new TestSuiteServiceImpl().getId(token);
        int jobId = new JobServiceImpl().getId(token);
        int testCaseId = new TestCaseServiceImpl().getId(token, testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().getId(token, testSuiteId, jobId);
        int testId = new TestServiceImpl().getId(token, testCaseId, testRunId);
        String putTestRs = apiExecutor.callApiMethod(
                new PutUpdateTestStatusMethod(token, testId, testSuiteId, jobId, expectedTestStatusValue),
                HTTPStatusCodeType.OK, true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String testStatus = JsonPath.from(putTestRs).get(JSONConstant.STATUS_KEY);
        Assert.assertEquals(testStatus, expectedTestStatusValue, "Test status was not updated!");
    }

    @Test
    public void testCreateTestArtifact() {
        TestServiceImpl testServiceImpl = new TestServiceImpl();
        String link = R.TESTDATA.get(ConfigConstant.LINK_KEY);
        String token = new APIContextManager().getAccessToken();
        int testSuiteId = new TestSuiteServiceImpl().getId(token);
        int jobId = new JobServiceImpl().getId(token);
        int testCaseId = new TestCaseServiceImpl().getId(token, testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().getId(token, testSuiteId, jobId);
        int testId = testServiceImpl.getId(token, testCaseId, testRunId);
        apiExecutor.callApiMethod(new PostCreateTestArtifactMethod(token, link, testId), HTTPStatusCodeType.OK, false,
                null);
        List<Integer> artifactId = testServiceImpl.getAllArtifacts(token, testRunId);
        LOGGER.info(String.format("Artifact ID: %s", artifactId.toString()));
        Assert.assertNotEquals(0, artifactId.get(0), "Test's artifact was not attache to test!");
    }

    @Test
    public void testDeleteTestById() {
        String token = new APIContextManager().getAccessToken();
        int testSuiteId = new TestSuiteServiceImpl().getId(token);
        int jobId = new JobServiceImpl().getId(token);
        int testCaseId = new TestCaseServiceImpl().getId(token, testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().getId(token, testSuiteId, jobId);
        int testId = new TestServiceImpl().getId(token, testCaseId, testRunId);
        apiExecutor.callApiMethod(new DeleteTestByIdMethod(token, testId), HTTPStatusCodeType.OK, false, null);
        apiExecutor.callApiMethod(new DeleteTestByIdMethod(token, testId), HTTPStatusCodeType.NOT_FOUND, false, null);
    }

    @Test
    public void testLinkWorkItemToTest() {
        String token = new APIContextManager().getAccessToken();
        String expectedJiraIdValue = R.TESTDATA.get(ConfigConstant.EXPECTED_JIRA_ID_KEY);
        String workItemType = R.TESTDATA.get(ConfigConstant.WORK_ITEM_TYPE_KEY);
        int testSuiteId = new TestSuiteServiceImpl().getId(token);
        int jobId = new JobServiceImpl().getId(token);
        int testCaseId = new TestCaseServiceImpl().getId(token, testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().getId(token, testSuiteId, jobId);
        int testId = new TestServiceImpl().getId(token, testCaseId, testRunId);
        String linkWorkItemRs = apiExecutor.callApiMethod(
                new PostLinkWorkItemMethod(token, testCaseId, expectedJiraIdValue, testId, workItemType),
                HTTPStatusCodeType.OK, false, null);
        String jiraId = JsonPath.from(linkWorkItemRs).get(JSONConstant.JIRA_ID_KEY);
        Assert.assertEquals(jiraId, expectedJiraIdValue, "Work item was not link to test!");
    }

    @Test
    public void testGetWorkItem() {
        String token = new APIContextManager().getAccessToken();
        String workItemType = R.TESTDATA.get(ConfigConstant.WORK_ITEM_TYPE_KEY);
        String jiraId = R.TESTDATA.get(ConfigConstant.EXPECTED_JIRA_ID_KEY);
        int testSuiteId = new TestSuiteServiceImpl().getId(token);
        int jobId = new JobServiceImpl().getId(token);
        int testCaseId = new TestCaseServiceImpl().getId(token, testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().getId(token, testSuiteId, jobId);
        int testId = new TestServiceImpl().getId(token, testCaseId, testRunId);
        apiExecutor.callApiMethod(new PostLinkWorkItemMethod(token, testCaseId, jiraId, testId, workItemType),
                HTTPStatusCodeType.OK, false, null);
        String workItemRs = apiExecutor.callApiMethod(new GetWorkItemMethod(token, testId, workItemType),
                HTTPStatusCodeType.OK, false, null);
        List<Integer> workItemId = JsonPath.from(workItemRs).getList(JSONConstant.ID_KEY);
        LOGGER.info(workItemId);
        Assert.assertFalse(workItemId.isEmpty());
        Assert.assertNotEquals(0, workItemId.get(0), "Work item was not create!");
    }

    @Test
    public void testCreateWorkItem() {
        String token = new APIContextManager().getAccessToken();
        String jiraId = R.TESTDATA.get(ConfigConstant.EXPECTED_JIRA_ID_KEY);
        int testSuiteId = new TestSuiteServiceImpl().getId(token);
        int jobId = new JobServiceImpl().getId(token);
        int testCaseId = new TestCaseServiceImpl().getId(token, testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().getId(token, testSuiteId, jobId);
        int testId = new TestServiceImpl().getId(token, testCaseId, testRunId);
        apiExecutor.callApiMethod(new PostCreateWorkItemMethod(token, testId, jiraId), HTTPStatusCodeType.OK, true,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

}
