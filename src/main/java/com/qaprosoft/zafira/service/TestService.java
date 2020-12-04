package com.qaprosoft.zafira.service;

import java.util.List;

public interface TestService {

    int create(int testCaseId, int testRunId);

    void finishTest(int testCaseId, int testRunId, int testId);

    List<Integer> getAllArtifacts(int testRunId);

    String getAllTest(int testRunId);

    void updateTestStatus(int testId, int testSuiteId, int
            jobId, String expectedTestStatusValue);

    void updateTestStacktraceLabel(int testId, String stacktraceLabelsName);
}
