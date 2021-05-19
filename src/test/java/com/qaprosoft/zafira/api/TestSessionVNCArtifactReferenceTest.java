package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.testSessionController.PostSessionV1Method;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.constant.TestRailConstant;
import com.qaprosoft.zafira.dataProvider.CapabilitiesManagerDataProvider;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.*;
import com.zebrunner.agent.core.annotation.TestLabel;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;

public class TestSessionVNCArtifactReferenceTest extends ZafiraAPIBaseTest {

    private final CapabilitiesManagerServiceImpl capabilitiesManagerService = new CapabilitiesManagerServiceImpl();
    private static final String PATH_TO_CHECK_VNC = R.TESTDATA.get(ConfigConstant.PATH_TO_CHECK_VNC);

    private int testRunId;

    @AfterMethod
    public void testDeleteTestRun() {
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
    }


    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40055")
    public void testCheckVNCLinkWithoutVNCLinksAndProviderInBodyRq() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_VNC);

        postSessionV1Method.addProperty(JSONConstant.ENABLE_VNC_DESIRED, true);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String sessionId = JsonPath.from(rs).getString(JSONConstant.SESSION_ID);
        String actualLink = capabilitiesManagerService.getVNCLink(testRunId, testSessionId);
        String url = new IntegrationInfoServiceImpl().getURLByIntegrationName("ZEBRUNNER");
        String expectedLink = url.replace("/wd/hub","").replaceFirst("http", "ws")
                + "/ws/vnc/" + sessionId;
        Assert.assertEquals(actualLink, expectedLink, "Link is not as expected!");
    }

    @Test()
    public void testCheckVNCLinkWithVNCEnableFalse() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_VNC);

        postSessionV1Method.addProperty(JSONConstant.ENABLE_VNC_DESIRED, false);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        JsonPath.from(rs).getString(JSONConstant.SESSION_ID);
        String actualLink = capabilitiesManagerService.getVNCLink(testRunId, testSessionId);
        Assert.assertNull(actualLink, "Link is not as expected!");
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40059")
    public void testCheckVNCLinkWithLinksOnAllPositions() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        String link = "_Link_".concat(RandomStringUtils.randomAlphabetic(80));
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_VNC);

        postSessionV1Method.addProperty(JSONConstant.ENABLE_VNC_DESIRED, true);
        postSessionV1Method.addProperty(JSONConstant.VNC_LINK_ACT_SLOT, JSONConstant.VNC_LINK_ACT_SLOT + link + "<session-id>");
        postSessionV1Method.addProperty(JSONConstant.VNC_LINK_ACT, JSONConstant.VNC_LINK_ACT + link);
        postSessionV1Method.addProperty(JSONConstant.VNC_LINK_DES_SLOT, JSONConstant.VNC_LINK_DES_SLOT + link);
        postSessionV1Method.addProperty(JSONConstant.VNC_LINK_DES, JSONConstant.VNC_LINK_DES + link);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualLink = capabilitiesManagerService.getVNCLink(testRunId, testSessionId);
        String sessionId = JsonPath.from(rs).getString(JSONConstant.SESSION_ID);
        Assert.assertEquals(actualLink, JSONConstant.VNC_LINK_ACT_SLOT + link + sessionId, "Link is not as expected!");
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40060")
    public void testCheckVNCLinkWithLinksOnAllPositionWithout_VNC_LINK_ACT_SLOT() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        String link = "_Link_".concat(RandomStringUtils.randomAlphabetic(80));
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_VNC);

        postSessionV1Method.addProperty(JSONConstant.ENABLE_VNC_DESIRED, true);
        postSessionV1Method.addProperty(JSONConstant.VNC_LINK_ACT, JSONConstant.VNC_LINK_ACT + link + "<session-id>");
        postSessionV1Method.addProperty(JSONConstant.VNC_LINK_DES_SLOT, JSONConstant.VNC_LINK_DES_SLOT + link);
        postSessionV1Method.addProperty(JSONConstant.VNC_LINK_DES, JSONConstant.VNC_LINK_DES + link);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualLink = capabilitiesManagerService.getVNCLink(testRunId, testSessionId);
        String sessionId = JsonPath.from(rs).getString(JSONConstant.SESSION_ID);
        Assert.assertEquals(actualLink, JSONConstant.VNC_LINK_ACT + link + sessionId, "Link is not as expected!");
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40061")
    public void testCheckVNCLinkWithoutLinksInActualCaps() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        String link = "_Link_".concat(RandomStringUtils.randomAlphabetic(80));
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_VNC);

        postSessionV1Method.addProperty(JSONConstant.ENABLE_VNC_DESIRED, true);
        postSessionV1Method.addProperty(JSONConstant.VNC_LINK_DES_SLOT, JSONConstant.VNC_LINK_DES_SLOT + link + "<session-id>");
        postSessionV1Method.addProperty(JSONConstant.VNC_LINK_DES, JSONConstant.VNC_LINK_DES + link);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualLink = capabilitiesManagerService.getVNCLink(testRunId, testSessionId);
        String sessionId = JsonPath.from(rs).getString(JSONConstant.SESSION_ID);
        Assert.assertEquals(actualLink, JSONConstant.VNC_LINK_DES_SLOT + link + sessionId, "Link is not as expected!");
    }

    @Test(dataProvider = "vncLink-positions", dataProviderClass = CapabilitiesManagerDataProvider.class)
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40062")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40063")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40064")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40065")
    public void testCheckVNCLinkEnableTrueWithLinkInPosition(String position) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        String link = "_Link_".concat(RandomStringUtils.randomAlphabetic(80));
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_VNC);

        postSessionV1Method.addProperty(JSONConstant.ENABLE_VNC_DESIRED, true);
        postSessionV1Method.addProperty(position, position + link + "<session-id>");

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualLink = capabilitiesManagerService.getVNCLink(testRunId, testSessionId);
        String sessionId = JsonPath.from(rs).getString(JSONConstant.SESSION_ID);
        Assert.assertEquals(actualLink, position + link + sessionId, "Link is not as expected!");
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40055")
    public void testCheckArtifactVNCNameWithoutVNCLinksInBodyRq() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_VNC);

        postSessionV1Method.addProperty(JSONConstant.ENABLE_VNC_DESIRED, true);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postSessionV1Method);
        String actualName = capabilitiesManagerService.getVNCArtifactName(testRunId);
        String expectedName = "\\bLive Video\\b \\d{2}:\\d{2}:\\d{2} \\bUTC\\b";
        Assert.assertTrue(actualName.matches(expectedName));
    }

    @Test(dataProvider = "test-environment-provider", dataProviderClass = CapabilitiesManagerDataProvider.class)
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40066")
    public void testCheckVNCLinkWithProviderInBodyRq(String provider) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_VNC);

        postSessionV1Method.addProperty(JSONConstant.ENABLE_VNC_DESIRED, true);
        postSessionV1Method.addProperty(JSONConstant.PROVIDER, provider);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String sessionId = JsonPath.from(rs).getString(JSONConstant.SESSION_ID);
        String actualLink = capabilitiesManagerService.getVNCLink(testRunId, testSessionId);
        String url = new IntegrationInfoServiceImpl().getURLByIntegrationName(provider);
//        String expectedLink = url.substring(0, url.indexOf("com") + 3).replaceFirst("http", "ws")
//                + "/ws/vnc/" + sessionId;
        String expectedLink = url.replace("/wd/hub","").replaceFirst("http", "ws")
              + "/ws/vnc/" + sessionId;
        Assert.assertEquals(actualLink, expectedLink, "Link is not as expected!");
    }

    @Test(dataProvider = "test-environment-provider", dataProviderClass = CapabilitiesManagerDataProvider.class)
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40067")
    public void testCheckVNCLinkWithProviderToLowerCaseInBodyRq(String provider) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_VNC);

        postSessionV1Method.addProperty(JSONConstant.ENABLE_VNC_DESIRED, true);
        postSessionV1Method.addProperty(JSONConstant.PROVIDER, provider.toLowerCase(Locale.ROOT));

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String sessionId = JsonPath.from(rs).getString(JSONConstant.SESSION_ID);
        String actualLink = capabilitiesManagerService.getVNCLink(testRunId, testSessionId);
        String url = new IntegrationInfoServiceImpl().getURLByIntegrationName(provider);
        String expectedLink = url.replace("/wd/hub","").replaceFirst("http", "ws")
                + "/ws/vnc/" + sessionId;
        Assert.assertEquals(actualLink, expectedLink, "Link is not as expected!");
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40068")
    public void testCheckVNCLinkWithNonexistentProvider() {
        testRunId = new TestRunServiceAPIImplV1().start();
        String provider =  "Provider_".concat(RandomStringUtils.randomAlphabetic(10));
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_VNC);

        postSessionV1Method.addProperty(JSONConstant.ENABLE_VNC_DESIRED, true);
        postSessionV1Method.addProperty(JSONConstant.PROVIDER, provider.toLowerCase(Locale.ROOT));

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualLink = capabilitiesManagerService.getVNCLink(testRunId, testSessionId);
       Assert.assertNull(actualLink, "Link is not as expected!");
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40069")
    public void testCheckVNCLinkAfterSessionFinish() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_VNC);

        postSessionV1Method.addProperty(JSONConstant.ENABLE_VNC_DESIRED, true);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);

        String actualName = capabilitiesManagerService.getVNCArtifactName(testRunId);
        String expectedName = "\\bLive Video\\b \\d{2}:\\d{2}:\\d{2} \\bUTC\\b";
        Assert.assertTrue(actualName.matches(expectedName));
        new TestSessionServiceImpl().finish(testRunId,testIds,testSessionId);
        String actualNameAfterFinish = capabilitiesManagerService.getVNCArtifactName(testRunId);
        Assert.assertNull(actualNameAfterFinish, "Link is not deleted!");
    }
}
