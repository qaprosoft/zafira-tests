package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.APIContextManager;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class JobTest extends ZariraAPIBaseTest {
    @Test
    public void testCreateJob() {
        APIContextManager manager = new APIContextManager();
        String token = manager.getAccessToken();
        apiExecutor.callApiMethod(new PostJobMethod(token), HTTPStatusCodeType.OK, true, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
