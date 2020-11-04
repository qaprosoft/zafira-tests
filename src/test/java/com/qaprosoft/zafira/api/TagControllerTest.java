package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.tagController.GetIntegrationInformationByTagMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceAPIV1Impl;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class TagControllerTest extends ZafiraAPIBaseTest {
    private static final String INTEGRATION_TAG = "TESTRAIL_TESTCASE_UUID";
    private final int TEST_RUN_ID = new TestRunServiceAPIImplV1().create();

    @AfterTest
    public void finishTestRun() {
        new TestRunServiceAPIImpl().deleteById(TEST_RUN_ID);
    }

    @Test
    public void testGetIntegrationInformationByTag() {
        String ciRunId = new TestRunServiceAPIImplV1().getCiRunId(TEST_RUN_ID);
        new TestServiceAPIV1Impl().createTest(TEST_RUN_ID);
        GetIntegrationInformationByTagMethod getIntegrationInformationByTagMethod = new GetIntegrationInformationByTagMethod(ciRunId, INTEGRATION_TAG);
        apiExecutor.expectStatus(getIntegrationInformationByTagMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getIntegrationInformationByTagMethod);
        apiExecutor.validateResponse(getIntegrationInformationByTagMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
