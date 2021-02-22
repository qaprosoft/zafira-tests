package com.qaprosoft.zafira.service;

import java.util.List;

public interface TestSessionControllerService {
    int create(int testRunId, int testId);

    int create(int testRunId, List testIds);

    void finish(int testRunId, List testIds, int testSessionId);

    void finish(int testRunId, int testId, int testSessionId);

    List getSessionsByTestRunId(int testRunId);

    List getSessionsByTestRunIdAndTestId(int testRunId, int testId);

    List getTestsInSessionsByTestRunId(int testRunId);
}
