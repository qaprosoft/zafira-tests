package com.qaprosoft.zafira.service;

import java.util.List;

public interface TestRunServiceAPI {

    int create(String accessToken, int testSuiteId, int jobId);

    String getCiRunId(String accessToken, int testSuiteId);

    String finishTestRun(String accessToken, int testRunId);

    List<Integer> getAll(String accessToken, String searchCriteriaType, int searchCriteriaId);
}
