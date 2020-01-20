package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.manager.APIContextManager;

public class PostCreateTestArtifactMethod extends ZafiraBaseApiMethodWithAuth {

    public PostCreateTestArtifactMethod(String link, int testId) {
        super("api/test/_post/rq_for_create_artifact.json", null, "api/test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testId));
        addProperty("link", link);
    }
}
