package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.testController.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.TestService;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

import java.util.List;

public class TestServiceImpl implements TestService {
    private static final Logger LOGGER = Logger.getLogger(TestServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int create(int testCaseId, int testRunId) {
        String response = apiExecutor.callApiMethod(new PostStartTestMethod(testCaseId, testRunId));
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void finishTest(int testCaseId, int testRunId, int testId) {
        apiExecutor.callApiMethod(new PostFinishTestMethod(testCaseId, testRunId, testId));
    }

    @Override
    public String getAllTest(int testRunId) {
        PostRetrieveTestBySearchCriteriaMethod postRetrieveTestBySearchCriteriaMethod = new PostRetrieveTestBySearchCriteriaMethod(testRunId);
        apiExecutor.expectStatus(postRetrieveTestBySearchCriteriaMethod, HTTPStatusCodeType.OK);
        return apiExecutor.callApiMethod(postRetrieveTestBySearchCriteriaMethod);
    }

    @Override
    public List<Integer> getAllTestIdsByTestRunId(int testRunId) {
        PostRetrieveTestBySearchCriteriaMethod postRetrieveTestBySearchCriteriaMethod = new PostRetrieveTestBySearchCriteriaMethod(testRunId);
        apiExecutor.expectStatus(postRetrieveTestBySearchCriteriaMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postRetrieveTestBySearchCriteriaMethod);
        List<Integer> listIds = JsonPath.from(rs).getList(JSONConstant.ALL_ID_FROM_RESULTS_V1_KEY);
        LOGGER.info(listIds);
        return listIds;
    }

    @Override
    public void updateTestStatus(int testId, int testSuiteId, int
            jobId, String expectedTestStatusValue) {
        PutUpdateTestStatusMethod putUpdateTestStatusMethod = new PutUpdateTestStatusMethod(testId, testSuiteId,
                jobId, expectedTestStatusValue);
        apiExecutor.expectStatus(putUpdateTestStatusMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putUpdateTestStatusMethod);
    }

    @Override
    public void linkWorkItem(int testId, int testCaseId) {
        String expectedJiraIdValue = R.TESTDATA.get(ConfigConstant.EXPECTED_JIRA_ID_KEY);
        PostLinkWorkItemMethod postLinkWorkItemMethod = new PostLinkWorkItemMethod(testCaseId, expectedJiraIdValue,
                testId, "BUG");
        apiExecutor.expectStatus(postLinkWorkItemMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postLinkWorkItemMethod);
    }

    @Override
    public void linkWorkItem(int testId, int testCaseId, String workItem) {
        PostLinkWorkItemMethod postLinkWorkItemMethod = new PostLinkWorkItemMethod(testCaseId, workItem,
                testId, "BUG");
        apiExecutor.expectStatus(postLinkWorkItemMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postLinkWorkItemMethod);
    }
}
