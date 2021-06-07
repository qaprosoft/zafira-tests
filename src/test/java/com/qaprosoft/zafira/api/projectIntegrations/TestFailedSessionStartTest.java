package com.qaprosoft.zafira.api.projectIntegrations;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraAPIBaseTest;
import com.qaprosoft.zafira.api.testSessionController.PostSessionV1Method;
import com.qaprosoft.zafira.api.testSessionController.PutSessionV1Method;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.ConstantName;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.constant.TestRailConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.CapabilitiesManagerServiceImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceV1Impl;
import com.qaprosoft.zafira.service.impl.TestSessionServiceImpl;
import com.zebrunner.agent.core.annotation.TestLabel;
import io.restassured.path.json.JsonPath;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class TestFailedSessionStartTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String PATH_FOR_FAILED_START_WITH_CAPABILITIES = R.TESTDATA.get(ConfigConstant.PATH_FOR_FAILED_START_WITH_CAPABILITIES);
    private final CapabilitiesManagerServiceImpl capabilitiesManagerService = new CapabilitiesManagerServiceImpl();
    private static TestSessionServiceImpl testSessionService = new TestSessionServiceImpl();
    private int testRunId;

    @AfterMethod
    public void testDeleteTestRun() {
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
    }

    @Test
    public void testStartFAILEDSession() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(R.TESTDATA.get(ConfigConstant.RQ_PATH_TO_CHECK_FAILED_SESSION));
        postSessionV1Method.setResponseTemplate(R.TESTDATA.get(ConfigConstant.RS_PATH_TO_CHECK_FAILED_SESSION));
        postSessionV1Method.addProperty("status", "FAILED");
        postSessionV1Method.addProperty("initiatedAt", OffsetDateTime.now(ZoneOffset.UTC).minusSeconds(3)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));

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
//        Assert.assertTrue(new TestSessionServiceImpl()
//                .getSessionsByTestRunId(testRunId)
//                .contains(sessionId));
//        List<Integer> actualTestIdsList = new TestSessionServiceImpl()
//               .getTestsInSessionsByTestRunId(testRunId);
//       actualTestIdsList.sort(Comparator.naturalOrder());
//        LOGGER.info("Actual testIds in test session " + actualTestIdsList.toString());
//        Assert.assertEquals(testIds, actualTestIdsList,
//                "The number of tests is not as expected!");
    }

    @Test
    public void testUpdateFAILEDSession() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        int sessionId = testSessionService.startFailedSession(testRunId, testIds.get(0));

        PutSessionV1Method putUpdateSessionV1Method =
                new PutSessionV1Method(testRunId, testIds, sessionId);
        apiExecutor.expectStatus(putUpdateSessionV1Method, HTTPStatusCodeType.FORBIDDEN);
        apiExecutor.callApiMethod(putUpdateSessionV1Method);
    }

    @Test
    public void testCheckBrowserNameWithoutBrowserNameInActualCapability() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_FAILED_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty("initiatedAt",OffsetDateTime.now(ZoneOffset.UTC).minusSeconds(3)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_BROWSER_NAME, ConstantName.BROWSER_NAME_IN_DESIRED_CAPABILITY);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);

//        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
//        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsBrowserName(testRunId, testSessionId);
//        Assert.assertEquals(actualBrowseName, ConstantName.BROWSER_NAME_IN_DESIRED_CAPABILITY, "Name is not as expected!");
    }
}
