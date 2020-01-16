package com.qaprosoft.zafira.service;

public interface TestRunServiceAPI {

    int getId(String accessToken, int testSuiteId, int jobId);

    String finishTestRun(String accessToken, int testRunId);

}
