package com.qaprosoft.zafira.api.artifactsController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutTestRunArtifactReferencesMethod extends ZafiraBaseApiMethodWithAuth {
    public PutTestRunArtifactReferencesMethod(int testRunId, int testId) {
        super("api/artifacts_controller/_post/artifact_references/rq_for_test.json",
                null, "api/file_util.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
    }
}

