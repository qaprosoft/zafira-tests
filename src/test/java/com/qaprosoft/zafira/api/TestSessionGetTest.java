package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.testSessionController.GetSessionByTestRunIdAndTestIdV1Method;
import com.qaprosoft.zafira.api.testSessionController.GetSessionByTestRunIdV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceV1Impl;
import com.qaprosoft.zafira.service.impl.TestSessionServiceImpl;
import com.zebrunner.agent.core.annotation.Maintainer;
import io.restassured.path.json.JsonPath;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Maintainer("obabich")
public class TestSessionGetTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private int testRunId;

    @AfterMethod
    public void testDeleteTestRun() {
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
    }

    @Test
    public void testGetSessionByTestRunIdAndTestId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceV1Impl().startTest(testRunId);
        int sessionId = new TestSessionServiceImpl().create(testRunId, testId);
        GetSessionByTestRunIdAndTestIdV1Method getSessionByTestAndTestRunId =
                new GetSessionByTestRunIdAndTestIdV1Method(testRunId, testId);
        apiExecutor.expectStatus(getSessionByTestAndTestRunId, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestAndTestRunId);
        int actualSessionId = JsonPath.from(rs).getInt(JSONConstant.ITEMS_TEST_ID_KEY);
        LOGGER.info("Actual sessionId:  " + actualSessionId);
        apiExecutor.validateResponse(getSessionByTestAndTestRunId,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(actualSessionId, sessionId, "Test session ID is not as expected! ");
    }

    @Test(description = "negative")
    public void testGetSessionByTestRunIdAndTestIdWithDeletedTestRunId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceV1Impl().startTest(testRunId);
        new TestSessionServiceImpl().create(testRunId, testId);
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
        GetSessionByTestRunIdAndTestIdV1Method getSessionByTestAndTestRunId
                = new GetSessionByTestRunIdAndTestIdV1Method(testRunId, testId);
        apiExecutor.expectStatus(getSessionByTestAndTestRunId, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getSessionByTestAndTestRunId);
    }

    @Test(description = "negative")
    public void testGetSessionByTestRunIdAndTestIdWithDeletedTestId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceV1Impl().startTest(testRunId);
        new TestSessionServiceImpl().create(testRunId, testId);
        new TestServiceV1Impl().deleteTest(testRunId, testId);
        GetSessionByTestRunIdAndTestIdV1Method getSessionByTestAndTestRunId
                = new GetSessionByTestRunIdAndTestIdV1Method(testRunId, testId);
        apiExecutor.expectStatus(getSessionByTestAndTestRunId, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getSessionByTestAndTestRunId);
    }

    @Test(description = "negative")
    public void testGetSessionByTestRunIdAndTestIdWithNegativeTestId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceV1Impl().startTest(testRunId);
        new TestSessionServiceImpl().create(testRunId, testId);

        GetSessionByTestRunIdAndTestIdV1Method getSessionByTestAndTestRunId
                = new GetSessionByTestRunIdAndTestIdV1Method(testRunId, testId * (-1));
        apiExecutor.expectStatus(getSessionByTestAndTestRunId, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getSessionByTestAndTestRunId);
    }

    @Test(description = "negative")
    public void testGetSessionByTestRunIdAndTestIdWithNegativeTestRunId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceV1Impl().startTest(testRunId);
        new TestSessionServiceImpl().create(testRunId, testId);
        GetSessionByTestRunIdAndTestIdV1Method getSessionByTestAndTestRunId
                = new GetSessionByTestRunIdAndTestIdV1Method(testRunId * (-1), testId);
        apiExecutor.expectStatus(getSessionByTestAndTestRunId, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getSessionByTestAndTestRunId);
    }

    @Test
    public void testGetSessionByTestRunId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceV1Impl().startTest(testRunId);
        int sessionId = new TestSessionServiceImpl().create(testRunId, testId);
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        getSessionByTestRunIdV1Method.addProperty("testId", String.valueOf(testId));
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int actualSessionId = JsonPath.from(rs).getInt(JSONConstant.ITEMS_SESSION_ID);
        List<Integer> listActualTestIds = JsonPath.from(rs).get(JSONConstant.ITEMS_TEST_ID);
        LOGGER.info(String.valueOf(actualSessionId));
        apiExecutor.validateResponse(getSessionByTestRunIdV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(actualSessionId, sessionId, "Test session ID is not as expected! ");
        Assert.assertTrue(listActualTestIds.contains(testId));
    }

    @Test(description = "negative")
    public void testGetSessionByTestRunIdWithDeletedTestRunId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceV1Impl().startTest(testRunId);
        new TestSessionServiceImpl().create(testRunId, testId);
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
    }

    @Test(description = "negative")
    public void testGetSessionByTestRunIdWithNegativeTestRunId() {
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceV1Impl().startTest(testRunId);
        new TestSessionServiceImpl().create(testRunId, testId);
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId * (-1));
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
    }

    /*  Agent version before 1.6 (test session start without "status" in body request)  */

    @Test
    public void testGetSessionByTestRunIdAndTestIdWithoutStatus() {
        List<Integer> testIdList = new ArrayList<>();
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceV1Impl().startTest(testRunId);
        testIdList.add(testId);
        int sessionId = new TestSessionServiceImpl().startWithoutStatus(testRunId, testIdList);
        GetSessionByTestRunIdAndTestIdV1Method getSessionByTestAndTestRunId =
                new GetSessionByTestRunIdAndTestIdV1Method(testRunId, testId);
        apiExecutor.expectStatus(getSessionByTestAndTestRunId, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestAndTestRunId);
        int actualSessionId = JsonPath.from(rs).getInt(JSONConstant.ITEMS_TEST_ID_KEY);
        LOGGER.info("Actual sessionId:  " + actualSessionId);
        apiExecutor.validateResponse(getSessionByTestAndTestRunId,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(actualSessionId, sessionId, "Test session ID is not as expected! ");
    }

    @Test
    public void testGetSessionByTestRunIdWithoutStatus() {
        List<Integer> testIdList = new ArrayList<>();
        testRunId = new TestRunServiceAPIImplV1().start();
        int testId = new TestServiceV1Impl().startTest(testRunId);
        testIdList.add(testId);
        int sessionId = new TestSessionServiceImpl().startWithoutStatus(testRunId, testIdList);
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        getSessionByTestRunIdV1Method.addProperty("testId", String.valueOf(testId));
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int actualSessionId = JsonPath.from(rs).getInt(JSONConstant.ITEMS_SESSION_ID);
        List<Integer> listActualTestIds = JsonPath.from(rs).get(JSONConstant.ITEMS_TEST_ID);
        LOGGER.info(String.valueOf(actualSessionId));
        apiExecutor.validateResponse(getSessionByTestRunIdV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(actualSessionId, sessionId, "Test session ID is not as expected! ");
        Assert.assertTrue(listActualTestIds.contains(testId));
    }
}
