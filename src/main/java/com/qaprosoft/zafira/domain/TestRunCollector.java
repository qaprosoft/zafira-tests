package com.qaprosoft.zafira.domain;

import com.qaprosoft.zafira.models.dto.JobType;
import com.qaprosoft.zafira.models.dto.TestCaseType;
import com.qaprosoft.zafira.models.dto.TestRunType;
import com.qaprosoft.zafira.models.dto.TestSuiteType;
import com.qaprosoft.zafira.models.dto.TestType;

import java.util.List;

public class TestRunCollector {

    private JobType jobType;
    private TestSuiteType testSuiteType;
    private TestCaseType testCaseType;
    private TestRunType testRunType;
    private List<TestType> testTypes;

    public TestRunCollector(JobType jobType, TestSuiteType testSuiteType, TestCaseType testCaseType, TestRunType testRunType, List<TestType> testTypes) {
        this.jobType = jobType;
        this.testSuiteType = testSuiteType;
        this.testCaseType = testCaseType;
        this.testRunType = testRunType;
        this.testTypes = testTypes;
    }

    public JobType getJobType() {
        return jobType;
    }

    public TestSuiteType getTestSuiteType() {
        return testSuiteType;
    }

    public TestCaseType getTestCaseType() {
        return testCaseType;
    }

    public TestRunType getTestRunType() {
        return testRunType;
    }

    public List<TestType> getTestTypes() {
        return testTypes;
    }

}
