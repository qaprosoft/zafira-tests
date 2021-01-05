package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.testSessionController.PostSessionV1Method;
import com.qaprosoft.zafira.api.testSessionController.PutSessionForLinkingV1Method;
import com.qaprosoft.zafira.api.testSessionController.PutSessionV1Method;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestSessionControllerServiceImpl;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class TestSessionControllerTest extends ZafiraAPIBaseTest {
    int testRunid = new TestRunServiceAPIImplV1().create();
    int id = new TestSessionControllerServiceImpl().create(testRunid);

    @AfterTest
    public void testFinishSession1() {
        new TestSessionControllerServiceImpl().finish(testRunid);
    }

    @Test
    public void testStartSession() {

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunid);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testFinishSession() {
        PutSessionV1Method putUpdateSessionV1Method = new PutSessionV1Method(id);
        apiExecutor.expectStatus(putUpdateSessionV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putUpdateSessionV1Method);
        apiExecutor.validateResponse(putUpdateSessionV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testLinkingTestExecutionsWithSession() {
        int id = new TestSessionControllerServiceImpl().create(testRunid);
        PutSessionForLinkingV1Method putSessionForLinkingV1Method = new PutSessionForLinkingV1Method(id);
        apiExecutor.expectStatus(putSessionForLinkingV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putSessionForLinkingV1Method);
        apiExecutor.validateResponse(putSessionForLinkingV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
