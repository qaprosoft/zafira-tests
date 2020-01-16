package com.qaprosoft.zafira.service;

import java.util.List;

public interface TestService {

    int getId(String accessToken, int testCaseId, int testRunId);

    void finishTest(String accessToken, int testCaseId, int testRunId, int testId);

    List<Integer> getAllArtifacts(String accessToken, int testRunId);

}
