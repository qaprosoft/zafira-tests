package com.qaprosoft.zafira.service.builder;

import com.qaprosoft.zafira.domain.Config;
import com.qaprosoft.zafira.exception.BuilderException;
import com.qaprosoft.zafira.models.db.Status;
import com.qaprosoft.zafira.models.db.TestRun;
import com.qaprosoft.zafira.models.dto.JobType;
import com.qaprosoft.zafira.models.dto.TestCaseType;
import com.qaprosoft.zafira.models.dto.TestRunType;
import com.qaprosoft.zafira.models.dto.TestSuiteType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestRunBuilder extends BaseBuilder {

    private final Builder builder;

    public TestRunBuilder(Builder builder) {
        this.builder = builder;
    }

    public static class Builder {

        private Map<TestBuilder.BuildStatus, Integer> buildStatuses = new HashMap<>();

        public Builder setPassedCount(int passedCount) {
            this.buildStatuses.put(TestBuilder.BuildStatus.PASSED, passedCount);
            return this;
        }

        public Builder setFailedCount(int failedCount) {
            this.buildStatuses.put(TestBuilder.BuildStatus.FAILED, failedCount);
            return this;
        }

        public Builder setKnownIssueCount(int knownIssueCount) {
            this.buildStatuses.put(TestBuilder.BuildStatus.KNOWN_ISSUE, knownIssueCount);
            return this;
        }

        public Builder setBlockerCount(int blockerCount) {
            this.buildStatuses.put(TestBuilder.BuildStatus.BLOCKER, blockerCount);
            return this;
        }

        public Builder setSkippedCount(int skippedCount) {
            this.buildStatuses.put(TestBuilder.BuildStatus.SKIPPED, skippedCount);
            return this;
        }

        public Builder setAbortedCount(int abortedCount) {
            this.buildStatuses.put(TestBuilder.BuildStatus.ABORTED, abortedCount);
            return this;
        }

        public Builder setInProgressCount(int inProgressCount) {
            this.buildStatuses.put(TestBuilder.BuildStatus.IN_PROGRESS, inProgressCount);
            return this;
        }

        public TestRunBuilder build() {
            return new TestRunBuilder(this);
        }

    }

    public Builder getBuilder() {
        return builder;
    }

    static TestRunType buildTestRun(Builder builder) {
        JobType job = JobBuilder.buildJob();
        TestSuiteType testSuite = TestSuiteBuilder.buildTestSuite();
        TestCaseType testCase = TestCaseBuilder.buildTestCase(testSuite.getId());
        TestRunType testRun = startTestRun(testSuite.getId(), job.getId());
        long testRunId = testRun.getId();
        builder.buildStatuses.forEach((status, count) -> {
            TestBuilder.buildTest(testRunId, testCase.getId(), testType -> {
                Status s = null;
                switch (status) {
                    case PASSED:
                        s = Status.PASSED;
                        break;
                    case KNOWN_ISSUE:
                        testType.setKnownIssue(true);
                    case BLOCKER:
                        testType.setBlocker(true);
                    case FAILED:
                        s = Status.FAILED;
                        break;
                    case SKIPPED:
                        s = Status.SKIPPED;
                        break;
                    case ABORTED:
                        s = Status.ABORTED;
                        break;
                    case IN_PROGRESS:
                        s = Status.IN_PROGRESS;
                        break;
                }
                testType.setStatus(s);
                return testType;
            });
        });
        testRun = finishTestRun(testRun);
        return testRun;
    }

    static TestRunType startTestRun(long testSuiteId, long jobId) {
        TestRunType testRunType = generateTestRun(testSuiteId, jobId);
        return buildItem(client -> client.startTestRun(testRunType))
                .orElseThrow(() -> new BuilderException("Test run was not started"));
    }

    static TestRunType finishTestRun(TestRunType testRunType) {
        return buildItem(client -> client.finishTestRun(testRunType.getId()))
                .orElseThrow(() -> new BuilderException("Test run was not finished"));
    }

    private static TestRunType generateTestRun(long testSuiteId, long jobId) {
        String ciRunId = UUID.randomUUID().toString();
        String configXml = null;
        try {
            configXml = FileUtils.readFileToString(new File(TestRunBuilder.class.getResource("/config.xml").getPath()), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        long userId = Config.ADMIN_ID.getLongValue();
        int buildNumber = RandomUtils.nextInt();
        TestRun.Initiator startedBy = TestRun.Initiator.HUMAN;
        return new TestRunType(ciRunId, testSuiteId, null, null , null, configXml, jobId, buildNumber, startedBy, null);
    }

}
