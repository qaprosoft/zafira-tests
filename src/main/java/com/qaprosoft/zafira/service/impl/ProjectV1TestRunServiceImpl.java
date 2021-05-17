package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.projectTestRuns.AttachTestRunToMilestoneMethod;
import com.qaprosoft.zafira.api.projectTestRuns.DeleteProjectTestRunByIdMethod;
import com.qaprosoft.zafira.api.projectTestRuns.GetProjectTestRunByTestRunIdMethod;
import com.qaprosoft.zafira.api.projectTestRuns.GetSearchProjectTestRunMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.ProjectV1TestRunService;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class ProjectV1TestRunServiceImpl implements ProjectV1TestRunService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public void deleteProjectTestRun(int testRunId) {
        DeleteProjectTestRunByIdMethod deleteProjectTestRunByIdMethod = new DeleteProjectTestRunByIdMethod(testRunId);
        apiExecutor.callApiMethod(deleteProjectTestRunByIdMethod);
    }

    @Override
    public List<Integer> getAllProjectTestRunIds(int projectId) {
        GetSearchProjectTestRunMethod getSearchProjectTestRunMethod =
                new GetSearchProjectTestRunMethod(projectId);
        apiExecutor.expectStatus(getSearchProjectTestRunMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSearchProjectTestRunMethod);
        List<Integer> listIds = JsonPath.from(rs).getList(JSONConstant.RESULT_ID_KEY);
        LOGGER.info("Existing projectTestRunId:  " + listIds);
        return listIds;
    }

    @Override
    public String getProjectTestRunById(int testRunId) {
        GetProjectTestRunByTestRunIdMethod projectTestRunByTestRunIdMethod =
                new GetProjectTestRunByTestRunIdMethod(testRunId);
        apiExecutor.expectStatus(projectTestRunByTestRunIdMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(projectTestRunByTestRunIdMethod);
        return rs;
    }

    @Override
    public String getProjectTestRunComment(int projectId, int testRunId) {
        GetSearchProjectTestRunMethod getSearchProjectTestRunMethod =
                new GetSearchProjectTestRunMethod(projectId);
        apiExecutor.expectStatus(getSearchProjectTestRunMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSearchProjectTestRunMethod);
        int indexOfId = JsonPath.from(rs).getList(JSONConstant.RESULT_ID_KEY).indexOf(testRunId);
        String comment = JsonPath.from(rs).getString("results[" + indexOfId + "].comments");
        LOGGER.info("Comments:  " + comment);
        return comment;
    }

    @Override
    public Boolean getProjectTestRunReviewedIs(int projectId, int testRunId) {
        GetSearchProjectTestRunMethod getSearchProjectTestRunMethod =
                new GetSearchProjectTestRunMethod(projectId);
        apiExecutor.expectStatus(getSearchProjectTestRunMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSearchProjectTestRunMethod);
        int indexOfId = JsonPath.from(rs).getList(JSONConstant.RESULT_ID_KEY).indexOf(testRunId);
        Boolean isReviewed = JsonPath.from(rs).getBoolean("results[" + indexOfId + "].reviewed");
        LOGGER.info("Reviewed:  " + isReviewed);
        return isReviewed;
    }

    @Override
    public void attachToMilestone(int testRunId, int milestoneId, int projectId) {
        AttachTestRunToMilestoneMethod attachTestRunToMilestoneMethod = new AttachTestRunToMilestoneMethod(testRunId, milestoneId, projectId);
        apiExecutor.expectStatus(attachTestRunToMilestoneMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(attachTestRunToMilestoneMethod);
    }
}
