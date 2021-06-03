package com.qaprosoft.zafira.api.artifactsController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.io.File;

public class PutTestArtifactReferencesMethod extends ZafiraBaseApiMethodWithAuth {
    public PutTestArtifactReferencesMethod(int testRunId, int testId) {
        super("api/artifacts_controller/artifact_references/_post/rq_for_test.json",
                null, "api/artifact.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
        replaceUrlPlaceholder("testId", String.valueOf(testId));
    }
}

