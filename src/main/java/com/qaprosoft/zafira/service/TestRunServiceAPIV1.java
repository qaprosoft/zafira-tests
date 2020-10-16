package com.qaprosoft.zafira.service;

import java.util.List;

public interface TestRunServiceAPIV1 {

    int create();

    String getCiRunId(int testSuiteId);

    String getTestRunResult(int testRunId);

    void finishTestRun(int testRunId);

}
