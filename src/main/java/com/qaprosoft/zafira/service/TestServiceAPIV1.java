package com.qaprosoft.zafira.service;


public interface TestServiceAPIV1 {

    int createTest(int testRunId);

    String updateResultInTest(int testRunId, int testId, String result);
}
