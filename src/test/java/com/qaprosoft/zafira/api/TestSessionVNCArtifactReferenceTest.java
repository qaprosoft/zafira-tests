package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.testSessionController.PostSessionV1Method;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.CapabilitiesManagerServiceImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceV1Impl;
import io.restassured.path.json.JsonPath;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class TestSessionVNCArtifactReferenceTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private CapabilitiesManagerServiceImpl capabilitiesManagerService = new CapabilitiesManagerServiceImpl();
    private static final String PATH_TO_CHECK_SESSION_ARTIFACTS = R.TESTDATA.get(ConfigConstant.PATH_TO_CHECK_SESSION_ARTIFACTS);
    private int testRunId;

    @AfterMethod
    public void testDeleteTestRun() {
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
    }


    @Test()
    public void testCheckVNCLinkEnableTrue() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_SESSION_ARTIFACTS);

        //    postSessionV1Method.addProperty(jsonConstantForLink, link);
        //    postSessionV1Method.addProperty(jsonConstantForEnable, true);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);

        //  new TestSessionServiceImpl().finish(testRunId, testIds, testSessionId);
        String actualLink = capabilitiesManagerService.getVNCLink(testRunId, testSessionId);
        String sessionId = JsonPath.from(rs).getString(JSONConstant.SESSION_ID);
        //String actualLinkInTests = capabilitiesManagerService.getArtifactReferencesInTest(testRunId, testIds.get(0), testSessionId, referenceType);
        //    Assert.assertEquals("vncLink", actualLink, "Link is not as expected!");
        Assert.assertEquals("wss://hub.zebrunner.com/ws/vnc/" + sessionId, actualLink, "Link is not as expected!");
        //  Assert.assertEquals(link, actualLink, "Link is not as expected!");
    }
}
