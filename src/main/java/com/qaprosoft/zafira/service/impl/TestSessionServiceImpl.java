package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.testSessionController.GetSessionByTestRunIdAndTestIdV1Method;
import com.qaprosoft.zafira.api.testSessionController.GetSessionByTestRunIdV1Method;
import io.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.testSessionController.PostSessionV1Method;
import com.qaprosoft.zafira.api.testSessionController.PutSessionV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.TestSessionService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestSessionServiceImpl implements TestSessionService {
    private static final Logger LOGGER = Logger.getLogger(TestSessionServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int create(int testRunId, int testId) {
        List<Integer> testIds = new ArrayList();
        testIds.add(testId);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postSessionV1Method);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public int create(int testRunId, List testIds) {
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postSessionV1Method);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void finish(int testRunId, List testIds, int testSessionId) {
        PutSessionV1Method putUpdateSessionV1Method = new PutSessionV1Method(testRunId, testIds, testSessionId);
        apiExecutor.expectStatus(putUpdateSessionV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putUpdateSessionV1Method);
        LOGGER.info("Test session with id=" + testSessionId + " was finished!");
    }

    @Override
    public void finish(int testRunId, int testId, int testSessionId) {
        List<Integer> testIds = new ArrayList();
        testIds.add(testId);
        PutSessionV1Method putUpdateSessionV1Method = new PutSessionV1Method(testRunId, testIds, testSessionId);
        apiExecutor.expectStatus(putUpdateSessionV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putUpdateSessionV1Method);
        LOGGER.info("Test session with id=" + testSessionId + " was finished!");
    }

    @Override
    public List<Integer> getSessionsByTestRunId(int testRunId) {
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        List<Integer> actualSessionIds = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID);
        return actualSessionIds;
    }

    @Override
    public List<Integer> getSessionsByTestRunIdAndTestId(int testRunId, int testId) {
        GetSessionByTestRunIdAndTestIdV1Method getSessionByTestAndTestRunId = new GetSessionByTestRunIdAndTestIdV1Method(testRunId, testId);
        apiExecutor.expectStatus(getSessionByTestAndTestRunId, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestAndTestRunId);
        List<Integer> actualSessionIds = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID);
        return actualSessionIds;
    }

    @Override
    public List<Integer> getTestsInSessionsByTestRunId(int testRunId) {
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        List<Integer> actualSessionIds = JsonPath.from(rs).get(JSONConstant.ITEMS_TEST_ID);
        actualSessionIds.sort(Comparator.naturalOrder());
        return actualSessionIds;
    }

    @Override
    public String getTestsInSessionsName(int testRunId, int testSessionId) {
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int testSessionIdIndex = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID).indexOf(testSessionId);
        String actualName = JsonPath.from(rs).get("items[" + testSessionIdIndex + "].name");
        return actualName;
    }

    @Override
    public String getTestsInSessionsBrowserName(int testRunId, int testSessionId) {
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int testSessionIdIndex = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID).indexOf(testSessionId);
        String actualName = JsonPath.from(rs).get("items[" + testSessionIdIndex + "].browserName");
        return actualName;
    }

    @Override
    public String getTestsInSessionsBrowserVersion(int testRunId, int testSessionId) {
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int testSessionIdIndex = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID).indexOf(testSessionId);
        String actualName = JsonPath.from(rs).get("items[" + testSessionIdIndex + "].browserVersion");
        return actualName;
    }

    @Override
    public String getTestsInSessionsPlatformName(int testRunId, int testSessionId) {
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int testSessionIdIndex = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID).indexOf(testSessionId);
        String actualName = JsonPath.from(rs).get("items[" + testSessionIdIndex + "].platformName");
        return actualName;
    }

    @Override
    public String getTestsInSessionsPlatformVersion(int testRunId, int testSessionId) {
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int testSessionIdIndex = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID).indexOf(testSessionId);
        String actualName = JsonPath.from(rs).get("items[" + testSessionIdIndex + "].platformVersion");
        return actualName;
    }

    @Override
    public String getSessionDeviseName(int testRunId, int testSessionId) {
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int testSessionIdIndex = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID).indexOf(testSessionId);
        String actualName = JsonPath.from(rs).get("items[" + testSessionIdIndex + "].deviceName");
        return actualName;
    }
}
