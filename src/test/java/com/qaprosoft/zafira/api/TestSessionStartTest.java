package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.testSessionController.*;
import com.qaprosoft.zafira.constant.ConstantName;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceV1Impl;
import com.qaprosoft.zafira.service.impl.TestSessionServiceImpl;
import io.restassured.path.json.JsonPath;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TestSessionStartTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private int testRunId;
    private List<Integer> nonExistentTestIds = new ArrayList();

    @DataProvider(name = "mandatoryFieldsForStar")
    public Object[][] getMandatoryFieldsForStart() {
        return new Object[][]{{JSONConstant.SESSION_ID},
                //  {JSONConstant.CAPABILITIES},
                //   {JSONConstant.DESIRED_CAPABILITIES},
                {JSONConstant.STARTED_AT}};
    }

    @AfterMethod
    public void testDeleteTestRun() {
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
    }

    @Test
    public void testStartSession() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 3);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        int sessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        List<Integer> actualTestIds = JsonPath.from(rs).getList(JSONConstant.TEST_IDS);
        LOGGER.info("Expected testIds:  " + testIds);
        LOGGER.info("Actual testIds:    " + actualTestIds);
        actualTestIds.sort(Comparator.naturalOrder());
        testIds.sort(Comparator.naturalOrder());
        Assert.assertEquals(actualTestIds, testIds,
                "The number of tests is not as expected!");
        Assert.assertTrue(new TestSessionServiceImpl()
                .getSessionsByTestRunId(testRunId)
                .contains(sessionId));
        List<Integer> actualTestIdsList = new TestSessionServiceImpl()
                .getTestsInSessionsByTestRunId(testRunId);
        actualTestIdsList.sort(Comparator.naturalOrder());
        LOGGER.info("Actual testIds in test session " + actualTestIdsList.toString());
        Assert.assertEquals(testIds, actualTestIdsList,
                "The number of tests is not as expected!");
    }

    @Test
    public void testStartSessionWithTheSameTestIds() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 3);
        testIds.add(testIds.get(0));

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        int sessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        List<Integer> actualTestIds = JsonPath.from(rs).getList(JSONConstant.TEST_IDS);
        Set<Integer> expectedTestIds = new HashSet<>(testIds);
        actualTestIds.sort(Comparator.naturalOrder());
        expectedTestIds.stream().sorted(Comparator.naturalOrder());
        LOGGER.info("Expected testIds: " + expectedTestIds);
        LOGGER.info("Actual testIds:   " + actualTestIds);

        Assert.assertEquals(actualTestIds, expectedTestIds,
                "The number of tests is not as expected!");
        Assert.assertTrue(new TestSessionServiceImpl()
                .getSessionsByTestRunId(testRunId)
                .contains(sessionId));
        List<Integer> actualTestIdsAfterGet = new TestSessionServiceImpl()
                .getTestsInSessionsByTestRunId(testRunId);
        LOGGER.info("Actual testIds in test session " + actualTestIdsAfterGet.toString());
        actualTestIdsAfterGet.sort(Comparator.naturalOrder());
        Assert.assertEquals(expectedTestIds, actualTestIdsAfterGet,
                "The number of tests is not as expected!");
    }

    @Test
    public void testStartSessionWithNonExistentTestId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 3);
        int delTestId = testIds.get(0);
        new TestServiceV1Impl().deleteTest(testRunId, delTestId);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
         testIds.remove(testIds.get(0));
        int sessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        List<Integer> actualTestIds = JsonPath.from(rs).getList(JSONConstant.TEST_IDS);
        testIds.sort(Comparator.naturalOrder());
        LOGGER.info("Expected testIds: " + testIds);
        LOGGER.info("Actual testIds:   " + actualTestIds);

        actualTestIds.sort(Comparator.naturalOrder());
        Assert.assertEquals(actualTestIds, testIds,
                "The number of tests is not as expected!");
        Assert.assertTrue(new TestSessionServiceImpl()
                .getSessionsByTestRunId(testRunId)
                .contains(sessionId));
        List<Integer> actualTestIdsAfterGet = new TestSessionServiceImpl()
                .getTestsInSessionsByTestRunId(testRunId);
        LOGGER.info("Actual testIds in test session " + actualTestIdsAfterGet.toString());
        actualTestIdsAfterGet.sort(Comparator.naturalOrder());
        Assert.assertEquals(testIds, actualTestIdsAfterGet,
                "The number of tests is not as expected!");
    }

    @Test
    public void testStartSessionWithNonExistentTestIdFromAnotherTestRun() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = startTestV1(testRunId);
        int testRunId1 = startTestRunV1();
        List<Integer> testIdsAnotherTestRun = startTestsV1(testRunId1, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIdsAnotherTestRun);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        int sessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        List<Integer> actualTestIds = JsonPath.from(rs).getList(JSONConstant.TEST_IDS);
        testIdsAnotherTestRun.removeAll(testIdsAnotherTestRun);
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId1);

        LOGGER.info("Expected testIds: " + testIdsAnotherTestRun);
        LOGGER.info("Actual testIds:   " + actualTestIds);

        actualTestIds.sort(Comparator.naturalOrder());
        Assert.assertEquals(actualTestIds, testIdsAnotherTestRun,
                "The number of tests is not as expected!");
        Assert.assertTrue(new TestSessionServiceImpl()
                .getSessionsByTestRunId(testRunId)
                .contains(sessionId));
        List<Integer> actualTestIdsAfterGet = new TestSessionServiceImpl()
                .getTestsInSessionsByTestRunId(testRunId);
        LOGGER.info("Actual testIds in test session " + actualTestIdsAfterGet.toString());
        actualTestIdsAfterGet.sort(Comparator.naturalOrder());
        Assert.assertEquals(testIdsAnotherTestRun, actualTestIdsAfterGet,
                "The number of tests is not as expected!");
    }

    @Test
    public void testStartSessionWithEmptyTestIds() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new ArrayList<>();

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        int sessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        List<Integer> actualTestIds = JsonPath.from(rs).getList(JSONConstant.TEST_IDS);
        Set<Integer> expectedTestIds = new HashSet<>(testIds);
        actualTestIds.sort(Comparator.naturalOrder());
        expectedTestIds.stream().sorted(Comparator.naturalOrder());
        LOGGER.info("Expected testIds: " + expectedTestIds);
        LOGGER.info("Actual testIds:   " + actualTestIds);
        Assert.assertEquals(actualTestIds, expectedTestIds,
                "The number of tests is not as expected!");

        Assert.assertTrue(new TestSessionServiceImpl()
                .getSessionsByTestRunId(testRunId)
                .contains(sessionId));
        List<Integer> actualTestIdsAfterGet = new TestSessionServiceImpl()
                .getTestsInSessionsByTestRunId(testRunId);
        actualTestIds.sort(Comparator.naturalOrder());
        LOGGER.info("Actual testIds in test session " + actualTestIdsAfterGet.toString());
        Assert.assertEquals(expectedTestIds, actualTestIdsAfterGet,
                "The number of tests is not as expected!");
    }

    @Test(description = "negative", dataProvider = "mandatoryFieldsForStar")
    public void testStartSessionWithoutField(String field) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.removeProperty(field);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postSessionV1Method);
    }

    @Test(description = "negative", dataProvider = "mandatoryFieldsForStar")
    public void testStartSessionWithEmptyField(String field) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.addProperty(field, ConstantName.EMPTY);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postSessionV1Method);
    }

    @Test(description = "negative")
    public void testStartSessionWithStartedAtInFuture() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.addProperty(JSONConstant.STARTED_AT,
                OffsetDateTime.now(ZoneOffset.UTC).plusHours(3)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postSessionV1Method);
    }

    @Test(description = "negative")
    public void testStartSessionWithNonExistingTestRunId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(postSessionV1Method);
    }

    @Test(description = "negative")
    public void testStartSessionWithNonExistingTestId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        new TestServiceV1Impl().deleteTest(testRunId, testIds.get(0));
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        List<Integer> actualTestIds = JsonPath.from(rs).get(JSONConstant.TEST_IDS);
        Assert.assertFalse(actualTestIds.contains(testIds.get(0)),
                "Test with id= " + testIds.get(0) + "had not to attached to session! ");
    }
}
