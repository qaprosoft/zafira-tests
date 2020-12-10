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

    @Test
    public void testGetIntegrationInformationByTag() {
        int testSuiteId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testCaseId = new TestCaseServiceImpl().create(testSuiteId);
        int testRunId = new TestRunServiceAPIImpl().create(testSuiteId, jobId);
        String ciRunId = new TestRunServiceAPIImplV1().getCiRunId(testRunId);
        new TestServiceImpl().create(testCaseId, testRunId);
        GetIntegrationInformationByTagMethod getIntegrationInformationByTagMethod = new GetIntegrationInformationByTagMethod(ciRunId, TOOL);
        apiExecutor.expectStatus(getIntegrationInformationByTagMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getIntegrationInformationByTagMethod);
        apiExecutor.validateResponse(getIntegrationInformationByTagMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        new TestRunServiceAPIImpl().deleteById(testRunId);
    }
}
