package com.qaprosoft.zafira.service;

import java.util.List;

public interface TestService {

    int create(int testCaseId, int testRunId);

    void finishTest(int testCaseId, int testRunId, int testId);

    String getAllTest(int testRunId);

    List<Integer> getAllTestIdsByTestRunId(int testRunId);

    void updateTestStatus(int testId, int testSuiteId, int
            jobId, String expectedTestStatusValue);

    void linkWorkItem(int testId, int testCaseId);
}
