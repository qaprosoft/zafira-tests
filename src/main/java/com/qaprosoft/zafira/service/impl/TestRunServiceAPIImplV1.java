package com.qaprosoft.zafira.service.impl;


import com.qaprosoft.zafira.api.testController.PostRetrieveTestBySearchCriteriaMethod;
import com.qaprosoft.zafira.api.testRunController.GetTestByTestRunIdMethod;
import com.qaprosoft.zafira.api.testRunController.GetTestRunByIdMethod;
import com.qaprosoft.zafira.api.testRunController.GetTestRunBySearchCriteriaMethod;
import com.qaprosoft.zafira.api.testRunController.v1.DeleteTestRunByIdV1Method;
import com.qaprosoft.zafira.api.testRunController.v1.PostStartTestRunV1Method;
import com.qaprosoft.zafira.api.testRunController.v1.PutFinishTestRunV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.service.TestRunServiceAPIV1;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

import java.time.OffsetDateTime;


public class TestRunServiceAPIImplV1 implements TestRunServiceAPIV1 {
    private static final Logger LOGGER = Logger.getLogger(TestRunServiceAPIImplV1.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int start() {
        String response = apiExecutor.callApiMethod(new PostStartTestRunV1Method(APIContextManager.PROJECT_NAME_KEY, OffsetDateTime.now().toString()));
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public String getCiRunId(int testRunId) {
        return JsonPath.from(
                apiExecutor.callApiMethod(new GetTestRunByIdMethod(testRunId)))
                .getString(JSONConstant.CI_RUN_ID_KEY);
    }

    @Override
    public String getTestRunResult(int testRunId) {
        return JsonPath.from(
                apiExecutor.callApiMethod(new GetTestRunByIdMethod(testRunId)))
                .getString(JSONConstant.STATUS_KEY);
    }

    @Override
    public String getTestRunById(int testRunId) {
        String rs = apiExecutor.callApiMethod(new GetTestRunByIdMethod(testRunId));
        LOGGER.info("TestRunResult:  " + rs);
        return apiExecutor.callApiMethod(new GetTestRunByIdMethod(testRunId));
    }

    @Override
    public void finishTestRun(int testRunId) {
        PutFinishTestRunV1Method putFinishTestRunV1Method = new PutFinishTestRunV1Method(testRunId, OffsetDateTime.now().toString());
        apiExecutor.expectStatus(putFinishTestRunV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putFinishTestRunV1Method);
    }

    @Override
    public void deleteTestRun(int testRunId) {
        DeleteTestRunByIdV1Method deleteTestRunByIdV1Method = new DeleteTestRunByIdV1Method(testRunId);
        apiExecutor.callApiMethod(deleteTestRunByIdV1Method);
        LOGGER.info("Test run with id=" + testRunId + " was deleted!");
    }

    @Override
    public String getTestResultsAfterFinishTestRun(int testRunId) {
        PostRetrieveTestBySearchCriteriaMethod postRetrieveTestBySearchCriteriaMethod = new PostRetrieveTestBySearchCriteriaMethod(testRunId);
        apiExecutor.expectStatus(postRetrieveTestBySearchCriteriaMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postRetrieveTestBySearchCriteriaMethod);
        return rs;
    }

    @Override
    public String getTestStatusAfterFinishTestRun(String testResults, int testRunId, int testId) {
        JsonPath.from(testResults).getList(JSONConstant.RESULT_ID_KEY);
        int id = JsonPath.from(testResults).getList("results.id").indexOf(testId);
        String actualStatus = JsonPath.from(testResults).getList("results.status").get(id).toString();
        return actualStatus;
    }

    @Override
    public String getTestResultsByTestId(int testRunId, int testId) {
        GetTestByTestRunIdMethod getTestByTestRunIdMethod = new GetTestByTestRunIdMethod(testRunId);
        apiExecutor.expectStatus(getTestByTestRunIdMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getTestByTestRunIdMethod);
        int id = JsonPath.from(rs).getList("id").indexOf(testId);
        LOGGER.info("TestId= " + testId);
        String testResult = JsonPath.from(rs).getJsonObject("[" + id + "]").toString();
        LOGGER.info("Test result with id = " + testId + " is " + testResult);
        return testResult;
    }

    @Override
    public String getTestStatusByTestId(int testRunId, int testId) {
        GetTestByTestRunIdMethod getTestByTestRunIdMethod = new GetTestByTestRunIdMethod(testRunId);
        apiExecutor.expectStatus(getTestByTestRunIdMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getTestByTestRunIdMethod);
        int id = JsonPath.from(rs).getList("id").indexOf(testId);
        LOGGER.info("TestId= " + testId);
        String testStatus = JsonPath.from(rs).getJsonObject("[" + id + "]" + ".status").toString();
        LOGGER.info("Test status with testId = " + testId + " is " + testStatus);
        return testStatus;
    }

    @Override
    public String getTestRunLabels(int testRunId) {
        GetTestRunBySearchCriteriaMethod getTestRunBySearchCriteriaMethod =
                new GetTestRunBySearchCriteriaMethod("id", testRunId);
        String rs = apiExecutor.callApiMethod(getTestRunBySearchCriteriaMethod);
        String labelsList = JsonPath.from(rs).getString("results.labels");
        LOGGER.info("Test run labels: " + labelsList);
        return labelsList;
    }
}

