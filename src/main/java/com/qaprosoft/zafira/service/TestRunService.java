package com.qaprosoft.zafira.service;

import com.qaprosoft.zafira.models.dto.TestRunType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TestRunService {

    void search(String query, boolean reviewed, String status, String environment, String platform, LocalDateTime date);

    CompletableFuture<List<TestRunType>> generateTestRuns(int count, int passedCount, int failedCount, int knownIssueCount, int blockerCount,
                                                         int skippedCount, int abortedCount, int inProgressCount, boolean toCount);

    CompletableFuture<List<TestRunType>> generateTestRuns(int count, boolean toCount);

}
