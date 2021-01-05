package com.qaprosoft.zafira.service;

public interface TestRunServiceAPIV1 {

    int create();

    String getCiRunId(int testSuiteId);

    String getTestRunResult(int testRunId);

    void finishTestRun(int testRunId);

    void deleteTestRun(int testRunId);
}
