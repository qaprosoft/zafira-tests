package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.testSessionController.*;
import com.qaprosoft.zafira.constant.ConstantName;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceAPIV1Impl;
import com.qaprosoft.zafira.service.impl.TestSessionControllerServiceImpl;
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

public class TestSessionControllerTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private int testRunId;
    private List nonExistentTestIds = new ArrayList();

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
        int testId = new TestServiceAPIV1Impl().startTest(testRunId);
        List<Integer> testIds = new TestServiceAPIV1Impl().startTests(testRunId, 3);
        testIds.add(testId);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        int sessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        List<Integer> actualTestIds = JsonPath.from(rs).getList(JSONConstant.TEST_IDS);
        Set<Integer> expectedTestIds = new HashSet<>(testIds);
        LOGGER.info("expectedTestIds:  " + expectedTestIds);
        LOGGER.info("actualIds:        " + actualTestIds);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(actualTestIds, expectedTestIds, "The number of tests is not as expected!");
        Assert.assertTrue(new TestSessionControllerServiceImpl().getSessionsByTestRunId(testRunId).contains(sessionId));
        List actualTestIdsList = new TestSessionControllerServiceImpl().getTestsInSessionsByTestRunId(testRunId);
        LOGGER.info("Actual testIds in test session " + actualTestIdsList.toString());
        testIds.forEach(testIdExp ->
                Assert.assertTrue(actualTestIdsList.contains(testIdExp), "Test with id = " + testIdExp + " absent in tests session!")
        );
    }

    @Test
    public void testStartSessionWithTheSameTestIds() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceAPIV1Impl().startTest(testRunId);
        List<Integer> testIds = new TestServiceAPIV1Impl().startTests(testRunId, 3);
        testIds.add(testId);
        testIds.add(testId);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        int sessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        List<Integer> actualTestIds = JsonPath.from(rs).getList(JSONConstant.TEST_IDS);
        Set<Integer> expectedTestIds = new HashSet<>(testIds);
        List<Integer> testIdsList = new ArrayList<>(expectedTestIds);
        LOGGER.info("expectedTestIds: " + expectedTestIds.size() + expectedTestIds);
        LOGGER.info("actualIds:        " + actualTestIds.size() + actualTestIds);
        Assert.assertEquals(actualTestIds, expectedTestIds, "The number of tests is not as expected!");
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertTrue(new TestSessionControllerServiceImpl().getSessionsByTestRunId(testRunId).contains(sessionId));
        List actualTestIdsList = new TestSessionControllerServiceImpl().getTestsInSessionsByTestRunId(testRunId);
        LOGGER.info("Actual testIds in test session " + actualTestIdsList.toString());
        testIds.forEach(testIdExp ->
                Assert.assertTrue(actualTestIdsList.contains(testIdExp), "Test with id = " + testIdExp + " absent in tests session!")
        );
    }

    @Test(description = "negative",dataProvider ="mandatoryFieldsForStar")
    public void testStartSessionWithoutField(String field) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceAPIV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.removeProperty(field);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postSessionV1Method);
    }

    @Test(description = "negative",dataProvider ="mandatoryFieldsForStar")
    public void testStartSessionWithEmptyField(String field) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceAPIV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.addProperty(field, ConstantName.EMPTY);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postSessionV1Method);
    }

    @Test(description = "negative")
    public void testStartSessionWithStartedAtInFuture() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceAPIV1Impl().startTests(testRunId, 1);
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
        List<Integer> testIds = new TestServiceAPIV1Impl().startTests(testRunId, 1);
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(postSessionV1Method);
    }

    @Test(description = "negative")
    public void testStartSessionWithNonExistingTestId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceAPIV1Impl().startTests(testRunId, 1);
        new TestServiceAPIV1Impl().deleteTest(testRunId,testIds.get(0));
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        List<Integer> actualTestIds = JsonPath.from(rs).get(JSONConstant.TEST_IDS);
        Assert.assertFalse(actualTestIds.contains(testIds.get(0)),
                "Test with id= " + testIds.get(0) + "had not to attached to session! ");
    }

    @Test
    public void testFinishSessionForAllTestInTestRun() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List testIds = new TestServiceAPIV1Impl().startTests(testRunId, 3);
        int sessionId = new TestSessionControllerServiceImpl().create(testRunId, testIds);
        testIds.sort(Comparator.naturalOrder());
        PutSessionV1Method putUpdateSessionV1Method = new PutSessionV1Method(testRunId, testIds, sessionId);
        apiExecutor.expectStatus(putUpdateSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(putUpdateSessionV1Method);
        apiExecutor.validateResponse(putUpdateSessionV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        List actualTestIds = JsonPath.from(rs).getList(JSONConstant.TEST_IDS);
        actualTestIds.sort(Comparator.naturalOrder());
        LOGGER.info("actualTestIds:" + actualTestIds);
        LOGGER.info("testIds" + testIds);
        Assert.assertEquals(actualTestIds, testIds);
        Assert.assertTrue(new TestSessionControllerServiceImpl().getSessionsByTestRunId(testRunId).contains(sessionId));
        List actualTestIdsList = new TestSessionControllerServiceImpl().getTestsInSessionsByTestRunId(testRunId);
        LOGGER.info("Actual testIds in test session " + actualTestIdsList.toString());
        testIds.forEach(testIdExp ->
                Assert.assertTrue(actualTestIdsList.contains(testIdExp), "Test with id = " + testIdExp + " absent in tests session!")
        );
    }

    @Test
    public void testLinkingTestExecutionsWithSession() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceAPIV1Impl().startTest(testRunId);
        int sessionId = new TestSessionControllerServiceImpl().create(testRunId, testId);
        List testIds = new TestServiceAPIV1Impl().startTests(testRunId, 3);
        PutLinkingTestToSessionMethod putLinkingTestToSessionMethod = new PutLinkingTestToSessionMethod(testRunId, testIds, sessionId);
        apiExecutor.expectStatus(putLinkingTestToSessionMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(putLinkingTestToSessionMethod);
        apiExecutor.validateResponse(putLinkingTestToSessionMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        testIds.add(testId);
        testIds.sort(Comparator.naturalOrder());
        List<Integer> actualTestIds = JsonPath.from(rs).get(JSONConstant.TEST_IDS);
        actualTestIds.sort(Comparator.naturalOrder());
        LOGGER.info("Actual testIds:  " + actualTestIds.toString());
        LOGGER.info("Expected testIds: " + testIds.toString());
        Assert.assertEquals(actualTestIds, testIds, "The number of tests is not as expected!");
        List actualTestIdsList = new TestSessionControllerServiceImpl().getTestsInSessionsByTestRunId(testRunId);
        LOGGER.info("Actual testIds in test session " + actualTestIdsList.toString());
        testIds.forEach(testIdExp ->
                Assert.assertTrue(actualTestIdsList.contains(testIdExp), "Test with id = " + testIdExp + " absent in tests session!")
        );
    }

    @Test
    public void testLinkingTestToSessionWithTheSameTestId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceAPIV1Impl().startTest(testRunId);
        int sessionId = new TestSessionControllerServiceImpl().create(testRunId, testId);
        List testIds = new TestServiceAPIV1Impl().startTests(testRunId, 3);
        testIds.add(testId);
        PutLinkingTestToSessionMethod putLinkingTestToSessionMethod = new PutLinkingTestToSessionMethod(testRunId, testIds, sessionId);
        apiExecutor.expectStatus(putLinkingTestToSessionMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(putLinkingTestToSessionMethod);
        apiExecutor.validateResponse(putLinkingTestToSessionMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        testIds.sort(Comparator.naturalOrder());
        List<Integer> actualTestIds = JsonPath.from(rs).get(JSONConstant.TEST_IDS);
        actualTestIds.sort(Comparator.naturalOrder());
        LOGGER.info("Actual testIds:  " + actualTestIds.toString());
        LOGGER.info("Expected testIds: " + testIds.toString());
        Assert.assertEquals(actualTestIds, testIds, "The number of tests is not as expected!");
        List actualTestIdsList = new TestSessionControllerServiceImpl().getTestsInSessionsByTestRunId(testRunId);
        LOGGER.info("Actual testIds in test session " + actualTestIdsList.toString());
        testIds.forEach(testIdExp ->
                Assert.assertTrue(actualTestIdsList.contains(testIdExp), "Test with id = " + testIdExp + " absent in tests session!")
        );
    }

    @Test
    public void testLinkingTestToSessionWithNonExistentTestId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List testIds = new TestServiceAPIV1Impl().startTests(testRunId, 1);
        int sessionId = new TestSessionControllerServiceImpl().create(testRunId, testIds);
        int testId = new TestServiceAPIV1Impl().startTest(testRunId);
        new TestServiceAPIV1Impl().deleteTest(testRunId, testId);
        nonExistentTestIds.add(testId);
        PutLinkingTestToSessionMethod putLinkingTestToSessionMethod = new PutLinkingTestToSessionMethod(testRunId, nonExistentTestIds, sessionId);
        apiExecutor.expectStatus(putLinkingTestToSessionMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(putLinkingTestToSessionMethod);
        apiExecutor.validateResponse(putLinkingTestToSessionMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        testIds.sort(Comparator.naturalOrder());
        List<Integer> actualTestIds = JsonPath.from(rs).get(JSONConstant.TEST_IDS);
        actualTestIds.sort(Comparator.naturalOrder());
        LOGGER.info("Actual testIds:  " + actualTestIds.toString());
        LOGGER.info("Expected testIds: " + testIds.toString());
        Assert.assertEquals(actualTestIds, testIds, "The number of tests is not as expected!");
        List actualTestIdsList = new TestSessionControllerServiceImpl().getTestsInSessionsByTestRunId(testRunId);
        LOGGER.info("Actual testIds in test session " + actualTestIdsList.toString());
        testIds.forEach(testIdExp ->
                Assert.assertTrue(actualTestIdsList.contains(testIdExp), "Test with id = " + testIdExp + " absent in tests session!")
        );
    }

    @Test(description = "negative")
    public void testLinkingTestToSessionWithNonExistentTestRunId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List testIds = new TestServiceAPIV1Impl().startTests(testRunId, 1);
        int sessionId = new TestSessionControllerServiceImpl().create(testRunId, testIds);
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
        PutLinkingTestToSessionMethod putLinkingTestToSessionMethod = new PutLinkingTestToSessionMethod(testRunId, nonExistentTestIds, sessionId);
        apiExecutor.expectStatus(putLinkingTestToSessionMethod, HTTPStatusCodeType.NOT_FOUND);
        String rs = apiExecutor.callApiMethod(putLinkingTestToSessionMethod);
    }

    @Test
    public void testGetSessionByTestRunIdAndTestId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceAPIV1Impl().startTest(testRunId);
        int sessionId = new TestSessionControllerServiceImpl().create(testRunId, testId);
        GetSessionByTestRunIdAndTestIdV1Method getSessionByTestAndTestRunId = new GetSessionByTestRunIdAndTestIdV1Method(testRunId, testId);
        apiExecutor.expectStatus(getSessionByTestAndTestRunId, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestAndTestRunId);
        int actualSessionId = JsonPath.from(rs).getInt(JSONConstant.ITEMS_TEST_ID_KEY);
        LOGGER.info("Actual sessionId:  " + actualSessionId);
        apiExecutor.validateResponse(getSessionByTestAndTestRunId, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(actualSessionId, sessionId, "Test session ID is not as expected! ");
    }

    @Test(description = "negative")
    public void testGetSessionByTestRunIdAndTestIdWithDeletedTestRunId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceAPIV1Impl().startTest(testRunId);
        int sessionId = new TestSessionControllerServiceImpl().create(testRunId, testId);
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
        GetSessionByTestRunIdAndTestIdV1Method getSessionByTestAndTestRunId = new GetSessionByTestRunIdAndTestIdV1Method(testRunId, testId);
        apiExecutor.expectStatus(getSessionByTestAndTestRunId, HTTPStatusCodeType.NOT_FOUND);
        String rs = apiExecutor.callApiMethod(getSessionByTestAndTestRunId);
    }

    @Test(description = "negative")
    public void testGetSessionByTestRunIdAndTestIdWithDeletedTestId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceAPIV1Impl().startTest(testRunId);
        int sessionId = new TestSessionControllerServiceImpl().create(testRunId, testId);
        new TestServiceAPIV1Impl().deleteTest(testRunId,testId);
        GetSessionByTestRunIdAndTestIdV1Method getSessionByTestAndTestRunId = new GetSessionByTestRunIdAndTestIdV1Method(testRunId, testId);
        apiExecutor.expectStatus(getSessionByTestAndTestRunId, HTTPStatusCodeType.NOT_FOUND);
        String rs = apiExecutor.callApiMethod(getSessionByTestAndTestRunId);
    }

    @Test
    public void testGetSessionByTestRunId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceAPIV1Impl().startTest(testRunId);
        int sessionId = new TestSessionControllerServiceImpl().create(testRunId, testId);
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        getSessionByTestRunIdV1Method.addProperty("testId", testId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int actualSessionId = JsonPath.from(rs).getInt(JSONConstant.ITEMS_SESSION_ID);
        List listActualTestIds = JsonPath.from(rs).get(JSONConstant.ITEMS_TEST_ID);
        LOGGER.info(String.valueOf(actualSessionId));
        apiExecutor.validateResponse(getSessionByTestRunIdV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(actualSessionId, sessionId, "Test session ID is not as expected! ");
        Assert.assertTrue(listActualTestIds.contains(testId));
    }

    @Test(description = "negative")
    public void testGetSessionByTestRunIdWithDeletedTestRunId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceAPIV1Impl().startTest(testRunId);
        int sessionId = new TestSessionControllerServiceImpl().create(testRunId, testId);
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.NOT_FOUND);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
    }
}
