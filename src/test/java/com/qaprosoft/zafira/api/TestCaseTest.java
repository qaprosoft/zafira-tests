package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.service.impl.TestSuiteServiceImpl;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class TestCaseTest extends ZariraAPIBaseTest {
    @Test
    public void testCreateTestCase() {
        int testSuiteId = new TestSuiteServiceImpl().create();
        apiExecutor.callApiMethod(new PostTestCaseMethod(testSuiteId), HTTPStatusCodeType.OK, true,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
