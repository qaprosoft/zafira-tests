package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.testSuiteController.GetTestSuiteMethod;
import com.qaprosoft.zafira.api.testSuiteController.PostTestSuiteMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.*;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class TestSuiteTest extends ZafiraAPIBaseTest {

    @Test
    public void testCreateTestSuite() {
        PostTestSuiteMethod postTestSuiteMethod = new PostTestSuiteMethod();
        apiExecutor.expectStatus(postTestSuiteMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postTestSuiteMethod);
        apiExecutor.validateResponse(postTestSuiteMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetTestSuite() {
        int testSuitId = new TestSuiteServiceImpl().create();
        int jobId = new JobServiceImpl().create();
        int testRunId = new TestRunServiceAPIImpl().create(testSuitId, jobId);
        GetTestSuiteMethod getTestSuiteMethod = new GetTestSuiteMethod(testSuitId);
        apiExecutor.expectStatus(getTestSuiteMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getTestSuiteMethod);
        apiExecutor.validateResponse(getTestSuiteMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
    }
}
