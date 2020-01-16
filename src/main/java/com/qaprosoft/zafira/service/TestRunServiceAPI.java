package com.qaprosoft.zafira.service;

public interface TestRunServiceAPI {

    int create(String accessToken, int testSuiteId, int jobId);

    String getCiRunId(String accessToken, int testSuiteId);

    String finishTestRun(String accessToken, int testRunId);

}
