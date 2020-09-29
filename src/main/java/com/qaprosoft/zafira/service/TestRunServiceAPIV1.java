package com.qaprosoft.zafira.service;

import java.util.List;

public interface TestRunServiceAPIV1 {

    int create();
    String getCiRunId(int testSuiteId);
    void finishTestRun(int testRunId);
}
