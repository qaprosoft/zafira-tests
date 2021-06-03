package com.qaprosoft.zafira.api.testController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PatchUpdateBatchPatchesOfTestStatusMethod extends ZafiraBaseApiMethodWithAuth {

    public PatchUpdateBatchPatchesOfTestStatusMethod(int testId, int testRunId, String expectedTestStatusValue) {
        super("api/test/_patch/rq.json", "/api/test/_patch/rs.json", "api/test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
        addProperty("id", String.valueOf(testId));
        addProperty("status", expectedTestStatusValue);
    }
}
