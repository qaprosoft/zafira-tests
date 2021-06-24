package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
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
    private static final String EXISTING_ISSUE = "ZEB-2787";


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
}
