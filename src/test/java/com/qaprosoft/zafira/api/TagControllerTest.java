package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.tagController.GetIntegrationInformationByTagMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceAPIV1Impl;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class TagControllerTest extends ZafiraAPIBaseTest {
    private static final String INTEGRATION_TAG = "TESTRAIL_TESTCASE_UUID";

    @Test
    public void testGetIntegrationInformationByTag() {
        int testRunId = new TestRunServiceAPIImplV1().create();
        String ciRunId = new TestRunServiceAPIImplV1().getCiRunId(testRunId);
        new TestServiceAPIV1Impl().createTest(testRunId);
        GetIntegrationInformationByTagMethod getIntegrationInformationByTagMethod = new GetIntegrationInformationByTagMethod(ciRunId, INTEGRATION_TAG);
        apiExecutor.expectStatus(getIntegrationInformationByTagMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getIntegrationInformationByTagMethod);
        apiExecutor.validateResponse(getIntegrationInformationByTagMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
    }
}
