package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.tagController.GetIntegrationInformationByTagMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.TestServiceAPIV1;
import com.qaprosoft.zafira.service.impl.*;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class TcmDataExportControllerTest extends ZafiraAPIBaseTest {
    private static final String TOOL = "testrail";
    private final int TESTS_TO_ADD = 1;

    @Test(enabled = false)
    public void testGetIntegrationInformationByTag() {
        int testRunId = createTestRun(TESTS_TO_ADD);
        String ciRunId = new TestRunServiceAPIImplV1().getCiRunId(testRunId);
        GetIntegrationInformationByTagMethod getIntegrationInformationByTagMethod = new GetIntegrationInformationByTagMethod(ciRunId, TOOL);
        apiExecutor.expectStatus(getIntegrationInformationByTagMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getIntegrationInformationByTagMethod);
        apiExecutor.validateResponse(getIntegrationInformationByTagMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        new TestRunServiceAPIImpl().deleteById(testRunId);
    }
}
