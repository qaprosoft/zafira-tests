package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
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
}
