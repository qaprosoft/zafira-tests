package com.qaprosoft.zafira.service;

import java.util.List;

public interface ProjectV1TestRunService {


    void deleteProjectTestRun(int testRunId);

    List<Integer> getAllProjectTestRunIds(int projectId);

    String getProjectTestRunById(int testRunId);

    int getAttachedToTestRunMilestoneId(int testRunId);

    String getProjectTestRunComment(int projectId, int testRunId);

    Boolean getProjectTestRunReviewedIs(int projectId, int testRunId);

    void attachToMilestone(int testRunId, int milestoneId, int projectId);
}
