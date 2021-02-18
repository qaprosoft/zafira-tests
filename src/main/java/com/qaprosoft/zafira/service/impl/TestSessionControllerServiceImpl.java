package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.testSessionController.GetSessionByTestRunIdV1Method;
import io.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.testSessionController.PostSessionV1Method;
import com.qaprosoft.zafira.api.testSessionController.PutSessionV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.TestSessionControllerService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TestSessionControllerServiceImpl implements TestSessionControllerService {
    private static final Logger LOGGER = Logger.getLogger(TestSessionControllerServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int create(int testRunId, int testId) {
        List testIds = new ArrayList();
        testIds.add(testId);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId,testIds);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postSessionV1Method);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
            }

    @Override
    public int create(int testRunId, List testIds) {
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId,testIds);
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
        List testIds = new ArrayList();
        testIds.add(testId);
        PutSessionV1Method putUpdateSessionV1Method = new PutSessionV1Method(testRunId, testIds, testSessionId);
        apiExecutor.expectStatus(putUpdateSessionV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putUpdateSessionV1Method);
        LOGGER.info("Test session with id=" + testSessionId + " was finished!");
    }

    @Override
    public List getSessionsByTestRunId(int testRunId) {
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        List actualSessionIds = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID);
        return actualSessionIds;
    }
}
