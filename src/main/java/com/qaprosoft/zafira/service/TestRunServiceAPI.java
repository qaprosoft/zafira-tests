package com.qaprosoft.zafira.service;

import java.util.List;

public interface TestRunServiceAPI {

    int create(int testSuiteId, int jobId);

    String getCiRunId(int testSuiteId);

    String finishTestRun(int testRunId);

    void deleteById(int testRunId);

    List<Integer> getAll(String searchCriteriaType, int searchCriteriaId);

}
