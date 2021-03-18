package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.testSessionController.PostSessionV1Method;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.ConstantName;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.dataProvider.CapabilitiesManagerDataProvider;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.CapabilitiesManagerServiceImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceV1Impl;
import io.restassured.path.json.JsonPath;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TestSessionCapabilitiesManagerTest extends ZafiraAPIBaseTest {

    private static final String PATH_FOR_START_WITH_CAPABILITIES = R.TESTDATA.get(ConfigConstant.PATH_FOR_START_WITH_CAPABILITIES);
    private int testRunId;
    private final CapabilitiesManagerServiceImpl capabilitiesManagerService = new CapabilitiesManagerServiceImpl();

    @AfterMethod
    public void testDeleteTestRun() {
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
    }

    /**
     * NAME_EXTRACTORS
     */

    @Test(dataProvider = "session-name", dataProviderClass = CapabilitiesManagerDataProvider.class)
    public void testCheckSessionNameWithNameInActualCapability(String jsonConstant, String value) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(jsonConstant, value);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualName = capabilitiesManagerService.getTestsInSessionsName(testRunId, testSessionId);
        Assert.assertEquals(value, actualName, "Name is not as expected!");
    }

    @Test
    public void testCheckSessionNameWithAllVariantsOfName() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_NAME, ConstantName.NAME_IN_ACTUAL_CAPABILITY);
        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_SESSION_NAME, ConstantName.SESSION_NAME_IN_ACTUAL_CAPABILITY);
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_NAME, ConstantName.NAME_IN_DESIRED_CAPABILITY);
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_SESSION_NAME, ConstantName.SESSION_NAME_IN_DESIRED_CAPABILITY);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualName = capabilitiesManagerService.getTestsInSessionsName(testRunId, testSessionId);
        Assert.assertEquals(ConstantName.NAME_IN_ACTUAL_CAPABILITY, actualName, "Name is not as expected!");
    }

    @Test
    public void testCheckSessionNameWithAllVariantsOfNameWithoutNameInActual() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_SESSION_NAME, ConstantName.SESSION_NAME_IN_ACTUAL_CAPABILITY);
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_NAME, ConstantName.NAME_IN_DESIRED_CAPABILITY);
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_SESSION_NAME, ConstantName.SESSION_NAME_IN_DESIRED_CAPABILITY);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualName = capabilitiesManagerService.getTestsInSessionsName(testRunId, testSessionId);
        Assert.assertEquals(ConstantName.SESSION_NAME_IN_ACTUAL_CAPABILITY, actualName, "Name is not as expected!");
    }

    @Test
    public void testCheckSessionNameWithoutActualNameAndActualSessionName() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_NAME, ConstantName.NAME_IN_DESIRED_CAPABILITY);
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_SESSION_NAME, ConstantName.SESSION_NAME_IN_DESIRED_CAPABILITY);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualName = capabilitiesManagerService.getTestsInSessionsName(testRunId, testSessionId);
        Assert.assertEquals(ConstantName.NAME_IN_DESIRED_CAPABILITY, actualName, "Name is not as expected!");
    }

    @Test
    public void testCheckSessionNameWithoutNameInBodyRq() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String sessionIdAct = JsonPath.from(rs).getString(JSONConstant.SESSION_ID);
        String actualName = capabilitiesManagerService.getTestsInSessionsName(testRunId, testSessionId);
        Assert.assertEquals(sessionIdAct, actualName, "Name is not as expected!");
    }

    @Test(enabled = false)
    public void testCheckSessionNameWithNameInDesiredAndEmptyActualCapability() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_NAME, ConstantName.NAME_IN_DESIRED_CAPABILITY);
        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_NAME, ConstantName.EMPTY_NAME_IN_ACTUAL_CAPABILITY);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.BAD_REQUEST);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualName = capabilitiesManagerService.getTestsInSessionsName(testRunId, testSessionId);
        Assert.assertEquals(ConstantName.NAME_IN_DESIRED_CAPABILITY, actualName,
                "Name is not as expected!");
    }

    @Test(enabled = false)
    public void testCheckSessionNameWithEmptyNameInActualCapability() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_NAME, ConstantName.EMPTY_NAME_IN_ACTUAL_CAPABILITY);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postSessionV1Method);
    }

    @Test(enabled = false)
    public void testCheckSessionNameWithEmptyNameInDesiredCapability() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_NAME, ConstantName.EMPTY_NAME_IN_DESIRED_CAPABILITY);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postSessionV1Method);
    }

    /**
     * BROWSER_NAME_EXTRACTORS
     */

    @Test
    public void testCheckBrowserName() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_BROWSER_NAME, ConstantName.BROWSER_NAME_IN_ACTUAL_CAPABILITY);
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_BROWSER_NAME, ConstantName.BROWSER_NAME_IN_DESIRED_CAPABILITY);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsBrowserName(testRunId, testSessionId);
        Assert.assertEquals(actualBrowseName, ConstantName.BROWSER_NAME_IN_ACTUAL_CAPABILITY, "Name is not as expected!");
    }

    @Test
    public void testCheckBrowserNameWithoutBrowserNameInActualCapability() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_BROWSER_NAME, ConstantName.BROWSER_NAME_IN_DESIRED_CAPABILITY);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsBrowserName(testRunId, testSessionId);
        Assert.assertNull(actualBrowseName, "Name is not as expected!");
    }

    @Test
    public void testCheckBrowserNameWithoutBrowserNameInRq() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsBrowserName(testRunId, testSessionId);
        Assert.assertNull(actualBrowseName, "Name is not as expected!");
    }

    /**
     * BROWSER_VERSION_EXTRACTORS
     */

    @Test(dataProvider = "browser-version", dataProviderClass = CapabilitiesManagerDataProvider.class)
    public void testCheckBrowserVersionWith(String jsonConstant, String value) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(jsonConstant, value);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsBrowserVersion(testRunId, testSessionId);
        Assert.assertEquals(actualBrowseName, value, "Name is not as expected!");
    }

    @Test
    public void testCheckBrowserVersionWithBrowserVersionAndVersionInActualCap() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_BROWSER_VERSION, ConstantName.BROWSER_VERSION_IN_ACTUAL_CAPABILITY);
        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_VERSION, ConstantName.ACTUAL_CAPS_VERSION);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsBrowserVersion(testRunId, testSessionId);
        Assert.assertEquals(actualBrowseName, ConstantName.BROWSER_VERSION_IN_ACTUAL_CAPABILITY, "Name is not as expected!");
    }

    @Test(dataProvider = "browser-version", dataProviderClass = CapabilitiesManagerDataProvider.class)
    public void testCheckDesiredBrowserVersionAndActualVariants(String jsonConstant, String value) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(jsonConstant, value);
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_BROWSER_VERSION, ConstantName.BROWSER_VERSION_IN_DESIRED_CAPABILITY);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsBrowserVersion(testRunId, testSessionId);
        Assert.assertEquals(actualBrowseName, value, "Name is not as expected!");
    }

    @Test
    public void testCheckBrowserVersionWithoutBrowserVersionInActualCap() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_BROWSER_VERSION, ConstantName.BROWSER_VERSION_IN_DESIRED_CAPABILITY);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsBrowserVersion(testRunId, testSessionId);
        Assert.assertNull(actualBrowseName, "Name is not as expected!");
    }

    @Test
    public void testCheckBrowserVersionWithoutBrowserVersionInRq() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsBrowserVersion(testRunId, testSessionId);
        Assert.assertNull(actualBrowseName, "Name is not as expected!");
    }

    /**
     * PLATFORM_NAME_EXTRACTORS
     */

    @Test(dataProvider = "platform-name", dataProviderClass = CapabilitiesManagerDataProvider.class)
    public void testCheckPlatformNameWith(String jsonConstant, String value) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(jsonConstant, value);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsPlatformName(testRunId, testSessionId);
        Assert.assertEquals(actualBrowseName, value, "Name is not as expected!");
    }

    @Test
    public void testCheckPlatformNameWithPlatformNameInSlotAndInDesired() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(JSONConstant.PLATFORM_NAME_SLOT, ConstantName.PLATFORM_NAME_IN_SLOT);
        postSessionV1Method.addProperty(JSONConstant.PLATFORM_NAME_DESIRED, ConstantName.PLATFORM_NAME_IN_DESIRED);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsPlatformName(testRunId, testSessionId);
        Assert.assertEquals(actualBrowseName, ConstantName.PLATFORM_NAME_IN_SLOT, "Name is not as expected!");
    }

    @Test
    public void testCheckPlatformNameWithAllPlatformVariants() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(JSONConstant.PLATFORM_NAME_SLOT, ConstantName.PLATFORM_NAME_IN_SLOT);
        postSessionV1Method.addProperty(JSONConstant.PLATFORM_NAME_DESIRED, ConstantName.PLATFORM_NAME_IN_DESIRED);
        postSessionV1Method.addProperty(JSONConstant.PLATFORM_NAME_ACT, ConstantName.PLATFORM_NAME_IN_ACT_CAPS);
        postSessionV1Method.addProperty(JSONConstant.PLATFORM_ACT, ConstantName.PLATFORM_IN_ACT_CAPS);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsPlatformName(testRunId, testSessionId);
        Assert.assertEquals(actualBrowseName, ConstantName.PLATFORM_NAME_IN_SLOT,
                "Name is not as expected!");
    }

    @Test
    public void testCheckPlatformNameWithoutPlatformNameInSlot() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(JSONConstant.PLATFORM_NAME_DESIRED, ConstantName.PLATFORM_NAME_IN_DESIRED);
        postSessionV1Method.addProperty(JSONConstant.PLATFORM_NAME_ACT, ConstantName.PLATFORM_NAME_IN_ACT_CAPS);
        postSessionV1Method.addProperty(JSONConstant.PLATFORM_ACT, ConstantName.PLATFORM_IN_ACT_CAPS);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsPlatformName(testRunId, testSessionId);
        Assert.assertEquals(actualBrowseName, ConstantName.PLATFORM_NAME_IN_DESIRED,
                "Name is not as expected!");
    }

    @Test
    public void testCheckPlatformNameWithoutPlatformNameInSlotAndDesired() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(JSONConstant.PLATFORM_NAME_ACT, ConstantName.PLATFORM_NAME_IN_ACT_CAPS);
        postSessionV1Method.addProperty(JSONConstant.PLATFORM_ACT, ConstantName.PLATFORM_IN_ACT_CAPS);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsPlatformName(testRunId, testSessionId);
        Assert.assertEquals(actualBrowseName, ConstantName.PLATFORM_NAME_IN_ACT_CAPS,
                "Name is not as expected!");
    }

    @Test
    public void testCheckPlatformNameWithoutPlatformNamesAndPlatform() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsPlatformName(testRunId, testSessionId);
        Assert.assertNull(actualBrowseName, "Name is not as expected!");
    }

    /**
     * PLATFORM_VERSION_EXTRACTORS
     */

    @Test
    public void testCheckPlatformVersionWithPlatformOnAllPositions() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);

        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);
        postSessionV1Method.addProperty(JSONConstant.PLATFORM_VERSION_SLOT, ConstantName.PLATFORM_VERSION_IN_SLOT);
        postSessionV1Method.addProperty(JSONConstant.VERSION_SLOT, ConstantName.VERSION_IN_SLOT);
        postSessionV1Method.addProperty(JSONConstant.PLATFORM_VERSION_ACT, ConstantName.PLATFORM_VERSION_IN_ACT_CAPS);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsPlatformVersion(testRunId, testSessionId);
        Assert.assertEquals(actualBrowseName, ConstantName.PLATFORM_VERSION_IN_SLOT,
                "Name is not as expected!");
    }

    @Test
    public void testCheckPlatformVersionWithoutPlatformVersionInSlot() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(JSONConstant.VERSION_SLOT, ConstantName.VERSION_IN_SLOT);
        postSessionV1Method.addProperty(JSONConstant.PLATFORM_VERSION_ACT, ConstantName.PLATFORM_VERSION_IN_ACT_CAPS);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsPlatformVersion(testRunId, testSessionId);
        Assert.assertEquals(actualBrowseName, ConstantName.VERSION_IN_SLOT,
                "Name is not as expected!");
    }

    @Test(dataProvider = "platform-version", dataProviderClass = CapabilitiesManagerDataProvider.class)
    public void testCheckPlatformVersionWith(String jsonConstant, String value) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(jsonConstant, value);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsPlatformVersion(testRunId, testSessionId);
        Assert.assertEquals(actualBrowseName, value, "Name is not as expected!");
    }

    @Test
    public void testCheckPlatformVersionWithoutPlatformVersionInRq() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getTestsInSessionsPlatformVersion(testRunId, testSessionId);
        Assert.assertNull(actualBrowseName, "Name is not as expected!");
    }

    /**
     * DEVICE_NAME_EXTRACTORS
     */

    @Test
    public void testCheckDeviseName() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(JSONConstant.DEVICE_NAME_SLOT, ConstantName.DEVICE_NAME_IN_SLOT);
        postSessionV1Method.addProperty(JSONConstant.DEVICE_MODEL_ACT, ConstantName.DEVICE_MODEL_IN_ACT_CAPS);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getSessionDeviseName(testRunId, testSessionId);
        Assert.assertEquals(actualBrowseName, ConstantName.DEVICE_NAME_IN_SLOT, "Name is not as expected!");
    }

    @Test(dataProvider = "devise-name", dataProviderClass = CapabilitiesManagerDataProvider.class)
    public void testCheckDeviseNameWith(String jsonConstant, String value) {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        postSessionV1Method.addProperty(jsonConstant, value);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getSessionDeviseName(testRunId, testSessionId);
        Assert.assertEquals(actualBrowseName, value, "Name is not as expected!");
    }

    @Test
    public void testCheckDeviseNameWithoutNameInRq() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);
        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);

        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = capabilitiesManagerService.getSessionDeviseName(testRunId, testSessionId);
        Assert.assertNull(actualBrowseName, "Name is not as expected!");
    }
}
