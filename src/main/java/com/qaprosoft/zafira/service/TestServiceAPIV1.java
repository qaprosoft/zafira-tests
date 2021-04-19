package com.qaprosoft.zafira.service;


import java.util.List;


public interface TestServiceAPIV1 {

    int startTest(int testRunId);

    int startTestWithMethodName(int testRunId, String methodName);

    List<Integer> startTests(int testRunId, int numOfTests);

    String finishTestAsResult(int testRunId, int testId, String result);

    int createTestHeadless(int testRunId);

    void deleteTest(int testRunId, int id);
}
