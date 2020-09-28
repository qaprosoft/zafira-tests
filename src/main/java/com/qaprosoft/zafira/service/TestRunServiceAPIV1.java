package com.qaprosoft.zafira.service;

import java.util.List;

public interface TestRunServiceAPIV1 {

    int create();

    String getCiRunId(int testSuiteId);

    String finishTestRun(int testRunId);

    void deleteById(int testRunId);

    List<Integer> getAll(String searchCriteriaType, int searchCriteriaId);

}
