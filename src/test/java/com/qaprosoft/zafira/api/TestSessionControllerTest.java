package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.testSessionController.GetSessionByTestRunIdV1Method;
import com.qaprosoft.zafira.api.testSessionController.PostSessionV1Method;
import com.qaprosoft.zafira.api.testSessionController.PutLinkingTestToSessionMethod;
import com.qaprosoft.zafira.api.testSessionController.PutSessionV1Method;
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
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;
import java.util.*;

public class TestSessionControllerTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    int testRunId;


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
        int sessionId=JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        List<Integer> actualTestIds = JsonPath.from(rs).getList(JSONConstant.TEST_IDS);
        Set<Integer> expectedTestIds = new HashSet<>(testIds);
        LOGGER.info("expectedTestIds:  " + expectedTestIds);
        LOGGER.info("actualIds:        " + actualTestIds);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(actualTestIds, expectedTestIds, "The number of tests is not as expected!");
        Assert.assertTrue(new TestSessionControllerServiceImpl().getSessionsByTestRunId(testRunId).contains(sessionId));
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
    }

    @Test
    public void testGetSessionById() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceAPIV1Impl().startTest(testRunId);
        int sessionId = new TestSessionControllerServiceImpl().create(testRunId, testId);
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        getSessionByTestRunIdV1Method.addProperty("testId", testId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int actualSessionId = JsonPath.from(rs).getInt(JSONConstant.ITEMS_TEST_ID_KEY);
        LOGGER.info(String.valueOf(actualSessionId));
        apiExecutor.validateResponse(getSessionByTestRunIdV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(actualSessionId, sessionId, "Test session ID is not as expected! ");
    }

    @Test
    public void testFinishSessionForAllTest() {
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
        LOGGER.info("actualTestIds:"+actualTestIds);
        LOGGER.info("testIds"+testIds);
        Assert.assertEquals(actualTestIds,testIds);
        Assert.assertTrue(new TestSessionControllerServiceImpl().getSessionsByTestRunId(testRunId).contains(sessionId));
    }

    @Test(enabled = false)
    public void testFinishSessionForSomeTest() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List testIds = new TestServiceAPIV1Impl().startTests(testRunId, 3);
        int sessionId = new TestSessionControllerServiceImpl().create(testRunId, testIds);
        testIds.remove(1);
        testIds.sort(Comparator.naturalOrder());
        PutSessionV1Method putUpdateSessionV1Method = new PutSessionV1Method(testRunId, testIds, sessionId);
        apiExecutor.expectStatus(putUpdateSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(putUpdateSessionV1Method);
        apiExecutor.validateResponse(putUpdateSessionV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        List actualTestIds = JsonPath.from(rs).getList(JSONConstant.TEST_IDS);
        actualTestIds.sort(Comparator.naturalOrder());
        LOGGER.info("actualTestIds:"+actualTestIds);
        LOGGER.info("testIds"+testIds);
        Assert.assertEquals(actualTestIds,testIds);
        Assert.assertTrue(new TestSessionControllerServiceImpl().getSessionsByTestRunId(testRunId).contains(sessionId));
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
        LOGGER.info("Actual testIds:  "+actualTestIds.toString());
        LOGGER.info("Expected testIds: "+testIds.toString());
        Assert.assertEquals(actualTestIds, testIds,"The number of tests is not as expected!");
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
        LOGGER.info("Actual testIds:  "+actualTestIds.toString());
        LOGGER.info("Expected testIds: "+testIds.toString());
        Assert.assertEquals(actualTestIds, testIds,"The number of tests is not as expected!");
    }
}
