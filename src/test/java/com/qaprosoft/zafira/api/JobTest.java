package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class JobTest extends ZariraAPIBaseTest {
    @Test
    public void testCreateJob() {
        PostJobMethod postJobMethod = new PostJobMethod();
        apiExecutor.expectStatus(postJobMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postJobMethod);
        apiExecutor.validateResponse(postJobMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
