package com.qaprosoft.zafira.service.impl;

import io.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.testSessionController.PostSessionV1Method;
import com.qaprosoft.zafira.api.testSessionController.PutSessionV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.TestSessionControllerService;
import org.apache.log4j.Logger;

public class TestSessionControllerServiceImpl implements TestSessionControllerService {
    private static final Logger LOGGER = Logger.getLogger(TestSessionControllerServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int create(int id) {
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(id);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postSessionV1Method);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void finish(int id) {
        PutSessionV1Method putUpdateSessionV1Method = new PutSessionV1Method(id);
        apiExecutor.expectStatus(putUpdateSessionV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putUpdateSessionV1Method);
    }
}
