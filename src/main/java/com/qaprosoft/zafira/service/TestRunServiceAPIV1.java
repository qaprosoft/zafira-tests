package com.qaprosoft.zafira.service;

public interface TestRunServiceAPIV1 {

    int create();

    String getCiRunId(int testSuiteId);

    String getTestRunResult(int testRunId);

    String getTestRunById(int testRunId);

    void finishTestRun(int testRunId);

    void deleteTestRun(int testRunId);

    String getTestResultsAfterFinishTestRun(int testRunId);

    String getTestStatusAfterFinishTestRun(String testResults,int testRunId, int testId);

    String getTestResultsByTestId(int testRunId, int testId);

    String getTestRunLabels(int testRunId);
}
