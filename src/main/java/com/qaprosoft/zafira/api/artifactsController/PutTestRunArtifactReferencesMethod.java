package com.qaprosoft.zafira.api.artifactsController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutTestRunArtifactReferencesMethod extends ZafiraBaseApiMethodWithAuth {
    public PutTestRunArtifactReferencesMethod(int testRunId) {
        super("api/artifacts_controller/artifact_references/_post/rq_for_test_run.json",
                null, "api/file_util.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
    }
}

