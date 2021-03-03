package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.testSessionController.PostSessionV1Method;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceV1Impl;
import com.qaprosoft.zafira.service.impl.TestSessionServiceImpl;
import io.restassured.path.json.JsonPath;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;
import java.util.*;

public class TestSessionCapabilitiesManagerTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private int testRunId;
    private static final String EMPTY_NAME_IN_ACTUAL_CAPABILITY = "";
    private static final String EMPTY_NAME_IN_DESIRED_CAPABILITY = "";
    private static final String BROWSER_NAME_IN_ACTUAL_CAPABILITY = "chrome";
    private static final String BROWSER_NAME_IN_DESIRED_CAPABILITY = "firefox";
    private static final String BROWSER_VERSION_IN_DESIRED_CAPABILITY = "77";
    private static final String BROWSER_VERSION_IN_ACTUAL_CAPABILITY = "76";
    private static final String ACTUAL_CAPS_VERSION = "88";
    private static final String PATH_FOR_START_WITH_CAPABILITIES = R.TESTDATA.get(ConfigConstant.PATH_FOR_START_WITH_CAPABILITIES);
    private static final String NAME_IN_ACTUAL_CAPABILITY = "ActualSessionName";
    private static final String NAME_IN_DESIRED_CAPABILITY = "DesiredSessionName";


    @AfterMethod
    public void testDeleteTestRun() {
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
    }

    @Test
    public void testCheckSessionNameWithNameInActualCapability() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);
        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_NAME, NAME_IN_ACTUAL_CAPABILITY);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualName = new TestSessionServiceImpl().getTestsInSessionsName(testRunId, testSessionId);

        Assert.assertEquals(NAME_IN_ACTUAL_CAPABILITY, actualName,
                "Name is not as expected!");
    }

    @Test(enabled = false)
    public void testCheckSessionNameWithEmptyNameInActualCapability() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);
        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_NAME, EMPTY_NAME_IN_ACTUAL_CAPABILITY);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postSessionV1Method);
    }

    @Test(enabled = false)
    public void testCheckSessionNameWithEmptyNameInDesiredCapability() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_NAME, EMPTY_NAME_IN_DESIRED_CAPABILITY);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postSessionV1Method);
    }

    @Test
    public void testCheckSessionNameWithNameInDesiredCapability() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_NAME, NAME_IN_DESIRED_CAPABILITY);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualName = new TestSessionServiceImpl().getTestsInSessionsName(testRunId, testSessionId);

        Assert.assertEquals(NAME_IN_DESIRED_CAPABILITY, actualName,
                "Name is not as expected!");
    }

    @Test
    public void testCheckSessionNameWithNameInDesiredAndActualCapability() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_NAME, NAME_IN_DESIRED_CAPABILITY);
        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_NAME, NAME_IN_ACTUAL_CAPABILITY);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualName = new TestSessionServiceImpl().getTestsInSessionsName(testRunId, testSessionId);

        Assert.assertEquals(NAME_IN_ACTUAL_CAPABILITY, actualName,
                "Name is not as expected!");
    }

    @Test(enabled = false)
    public void testCheckSessionNameWithNameInDesiredAndEmptyActualCapability() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_NAME, NAME_IN_DESIRED_CAPABILITY);
        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_NAME, EMPTY_NAME_IN_ACTUAL_CAPABILITY);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.BAD_REQUEST);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualName = new TestSessionServiceImpl().getTestsInSessionsName(testRunId, testSessionId);

        Assert.assertEquals(NAME_IN_DESIRED_CAPABILITY, actualName,
                "Name is not as expected!");
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

        String actualName = new TestSessionServiceImpl().getTestsInSessionsName(testRunId, testSessionId);

        Assert.assertEquals(sessionIdAct, actualName,
                "Name is not as expected!");
    }

    @Test
    public void testCheckBrowserName() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);
        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_BROWSER_NAME, BROWSER_NAME_IN_ACTUAL_CAPABILITY);
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_BROWSER_NAME, BROWSER_NAME_IN_DESIRED_CAPABILITY);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = new TestSessionServiceImpl().getTestsInSessionsBrowserName(testRunId, testSessionId);

        Assert.assertEquals(actualBrowseName, BROWSER_NAME_IN_ACTUAL_CAPABILITY,
                "Name is not as expected!");
    }

    @Test
    public void testCheckBrowserNameWithoutBrowserNameInActualCapability() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_BROWSER_NAME, BROWSER_NAME_IN_DESIRED_CAPABILITY);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = new TestSessionServiceImpl().getTestsInSessionsBrowserName(testRunId, testSessionId);

        Assert.assertEquals(actualBrowseName, null,
                "Name is not as expected!");
    }

    @Test
    public void testCheckBrowserVersion() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);
        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_BROWSER_VERSION, BROWSER_VERSION_IN_ACTUAL_CAPABILITY);
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_BROWSER_VERSION, BROWSER_VERSION_IN_DESIRED_CAPABILITY);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = new TestSessionServiceImpl().getTestsInSessionsBrowserVersion(testRunId, testSessionId);

        Assert.assertEquals(actualBrowseName, BROWSER_VERSION_IN_ACTUAL_CAPABILITY,
                "Name is not as expected!");
    }

    @Test
    public void testCheckBrowserVersionWithoutBrowserVersionInActualCap() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_BROWSER_VERSION, BROWSER_VERSION_IN_DESIRED_CAPABILITY);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = new TestSessionServiceImpl().getTestsInSessionsBrowserVersion(testRunId, testSessionId);

        Assert.assertEquals(actualBrowseName, null,
                "Name is not as expected!");
    }

    @Test
    public void testCheckBrowserVersionWithBrowserVersionAndVersionInActualCap() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);
        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_BROWSER_VERSION, BROWSER_VERSION_IN_ACTUAL_CAPABILITY);
        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_VERSION, ACTUAL_CAPS_VERSION);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = new TestSessionServiceImpl().getTestsInSessionsBrowserVersion(testRunId, testSessionId);

        Assert.assertEquals(actualBrowseName, BROWSER_VERSION_IN_ACTUAL_CAPABILITY,
                "Name is not as expected!");
    }

    @Test
    public void testCheckBrowserVersionWithoutBrowserVersionAndWithVersionInActualCap() {
        testRunId = new TestRunServiceAPIImplV1().start();
        List<Integer> testIds = new TestServiceV1Impl().startTests(testRunId, 1);

        PostSessionV1Method postSessionV1Method = new PostSessionV1Method(testRunId, testIds);
        postSessionV1Method.setRequestTemplate(PATH_FOR_START_WITH_CAPABILITIES);
        postSessionV1Method.addProperty(JSONConstant.ACTUAL_CAPS_VERSION, ACTUAL_CAPS_VERSION);
        postSessionV1Method.addProperty(JSONConstant.DESIRED_CAPS_BROWSER_VERSION, BROWSER_VERSION_IN_DESIRED_CAPABILITY);
        apiExecutor.expectStatus(postSessionV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSessionV1Method);
        apiExecutor.validateResponse(postSessionV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        int testSessionId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        String actualBrowseName = new TestSessionServiceImpl().getTestsInSessionsBrowserVersion(testRunId, testSessionId);

        Assert.assertEquals(actualBrowseName, ACTUAL_CAPS_VERSION,
                "Name is not as expected!");
    }
}
