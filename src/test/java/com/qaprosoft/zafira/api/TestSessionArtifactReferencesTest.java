package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.testSessionController.PostSessionV1Method;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.dataProvider.CapabilitiesManagerDataProvider;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.CapabilitiesManagerServiceImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceV1Impl;
import com.qaprosoft.zafira.service.impl.TestSessionServiceImpl;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class TestSessionArtifactReferencesTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private CapabilitiesManagerServiceImpl capabilitiesManagerService = new CapabilitiesManagerServiceImpl();
    private static final String PATH_TO_CHECK_SESSION_ARTIFACTS = R.TESTDATA.get(ConfigConstant.PATH_TO_CHECK_SESSION_ARTIFACTS);
    private int testRunId;

    @AfterMethod
    public void testDeleteTestRun() {
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
    }

    @Test(dataProvider = "session-artifact-references", dataProviderClass = CapabilitiesManagerDataProvider.class)
    public void testCheckArtifactReferenceLinkEnableTrue(String referenceType, String jsonConstantForLink, String jsonConstantForEnable) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        String link = "Link_".concat(referenceType + "_").concat(RandomStringUtils.randomAlphabetic(90));
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_SESSION_ARTIFACTS);

        postSessionV1Method.addProperty(jsonConstantForLink, link);
        postSessionV1Method.addProperty(jsonConstantForEnable, true);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        new TestSessionServiceImpl().finish(testRunId, testIds, testSessionId);
        String actualLink = capabilitiesManagerService.getArtifactReferences(testRunId, testSessionId, referenceType);
        String actualLinkInTests = capabilitiesManagerService.getArtifactReferencesInTest(testRunId, testIds.get(0), testSessionId, referenceType);
        Assert.assertEquals(actualLinkInTests, actualLink, "Link is not as expected!");
        Assert.assertEquals(link, actualLink, "Link is not as expected!");
    }

    @Test(dataProvider = "session-artifact-references", dataProviderClass = CapabilitiesManagerDataProvider.class)
    public void testCheckArtifactReferenceLinkEnableFalse(String referenceType, String jsonConstantForLink, String jsonConstantForEnable) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        String link = "Link_".concat(referenceType + "_").concat(RandomStringUtils.randomAlphabetic(90));
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_SESSION_ARTIFACTS);

        postSessionV1Method.addProperty(jsonConstantForLink, link);
        postSessionV1Method.addProperty(jsonConstantForEnable, false);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        new TestSessionServiceImpl().finish(testRunId, testIds, testSessionId);
        String actualLink = capabilitiesManagerService.getArtifactReferences(testRunId, testSessionId, referenceType);
        String actualLinkInTests = capabilitiesManagerService.getArtifactReferencesInTest(testRunId, testIds.get(0), testSessionId, referenceType);
        Assert.assertEquals(actualLinkInTests, actualLink, "Link is not as expected!");
        Assert.assertNull(actualLink, "Link is not as expected!");
    }

    @Test(dataProvider = "session-artifact-references", dataProviderClass = CapabilitiesManagerDataProvider.class)
    public void testCheckArtifactReferenceLinkWithEmptyLinkInRq(String referenceType, String jsonConstantForLink, String jsonConstantForEnable) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_SESSION_ARTIFACTS);

        postSessionV1Method.addProperty(jsonConstantForLink, "");
        postSessionV1Method.addProperty(jsonConstantForEnable, true);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        new TestSessionServiceImpl().finish(testRunId, testIds, testSessionId);
        String actualLink = capabilitiesManagerService.getArtifactReferences(testRunId, testSessionId, referenceType);
        Assert.assertEquals("", actualLink, "Link is not as expected!");
    }

    @Test(dataProvider = "session-artifact-references", dataProviderClass = CapabilitiesManagerDataProvider.class)
    public void testCheckArtifactReferenceLinkEnableTrueActCaps(String referenceType, String jsonConstantForLink, String jsonConstantForEnable) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        String link = "Link_".concat(referenceType + "_" + "Act_").concat(RandomStringUtils.randomAlphabetic(90));
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_SESSION_ARTIFACTS);

        postSessionV1Method.addProperty(jsonConstantForLink + "Act", link);
        postSessionV1Method.addProperty(jsonConstantForEnable + "Act", true);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        new TestSessionServiceImpl().finish(testRunId, testIds, testSessionId);
        String actualLink = capabilitiesManagerService.getArtifactReferences(testRunId, testSessionId, referenceType);
        Assert.assertNull(actualLink, "Link is not as expected!");
    }

    @Test(dataProvider = "session-artifact-references", dataProviderClass = CapabilitiesManagerDataProvider.class)
    public void testCheckArtifactReferenceLinkWithPlaceholderSessionId(String referenceType, String jsonConstantForLink, String jsonConstantForEnable) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_SESSION_ARTIFACTS);

        postSessionV1Method.addProperty(jsonConstantForLink, "<session-id>" + "/link/" + "<session-id>" + "/link");
        postSessionV1Method.addProperty(jsonConstantForEnable, true);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String sessionId = JsonPath.from(rs).getString(JSONConstant.SESSION_ID);
        new TestSessionServiceImpl().finish(testRunId, testIds, testSessionId);
        String actualLink = capabilitiesManagerService.getArtifactReferences(testRunId, testSessionId, referenceType);
        String expectedLink = sessionId + "/link/" + sessionId + "/link";
        String actualLinkInTests = capabilitiesManagerService.getArtifactReferencesInTest(testRunId, testIds.get(0), testSessionId, referenceType);
        Assert.assertEquals(expectedLink, actualLinkInTests, "Link is not as expected!");
        Assert.assertEquals(expectedLink, actualLink, "Link is not as expected!");
    }

    @Test(dataProvider = "session-artifact-references-to-check-link", dataProviderClass = CapabilitiesManagerDataProvider.class)
    public void testCheckArtifactReferenceLinkEnableTrueWithoutLink(String referenceType, String jsonConstantForEnable, String endOfLink) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_TO_CHECK_SESSION_ARTIFACTS);

        postSessionV1Method.addProperty(jsonConstantForEnable, true);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String sessionId = JsonPath.from(rs).getString(JSONConstant.SESSION_ID);
        new TestSessionServiceImpl().finish(testRunId, testIds, testSessionId);
        String actualLink = capabilitiesManagerService.getArtifactReferences(testRunId, testSessionId, referenceType);
        Assert.assertEquals(actualLink, "artifacts/test-sessions/" + sessionId + endOfLink, "Link is not as expected!");
    }
}
