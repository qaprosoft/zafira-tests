package com.qaprosoft.zafira.service.builder;

import com.qaprosoft.zafira.exception.BuilderException;
import com.qaprosoft.zafira.models.db.Status;
import com.qaprosoft.zafira.models.dto.TestType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class TestBuilder extends BaseBuilder {

    public enum BuildStatus {
        PASSED, FAILED, KNOWN_ISSUE, BLOCKER, SKIPPED, ABORTED, IN_PROGRESS
    }

    static TestType buildTest(long testRunId, long testCaseId, Function<TestType, TestType> testTypeFunction) {
        TestType testType = startTest(testRunId, testCaseId);
        testType = testTypeFunction.apply(testType);
        return finishTest(testType);
    }

    static TestType startTest(long testRunId, long testCaseId) {
        TestType testType = generateTest(testRunId, testCaseId);
        return buildItem(client -> client.startTest(testType))
                .orElseThrow(() -> new BuilderException("Test was not started"));
    }

    static TestType finishTest(TestType testType) {
        return buildItem(client -> client.finishTest(testType))
                .orElseThrow(() -> new BuilderException("Test was not finished"));
    }

    private static TestType generateTest(long testRunId, long testCaseId) {
        String configXml = null;
        try
        {
            configXml = FileUtils.readFileToString(new File(TestBuilder.class.getResource("/config.xml").getPath()), "UTF-8");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        String name = generateRandomString();
        Status status = Status.IN_PROGRESS;
        String testArgs = generateRandomString();
        String testGroup = "com.qaprosoft." + generateRandomString();
        long startTime = System.currentTimeMillis();
        String dependsOnMethods = "";
        List<String> workItems = Collections.singletonList("TEST#" + RandomUtils.nextInt());
        return new TestType(name, status, testArgs, testRunId, testCaseId, startTime, workItems, 0 , configXml);
    }

}
