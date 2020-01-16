package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;
import java.util.UUID;

public class PostStartTestMethod extends AbstractApiMethodV2 {

    public PostStartTestMethod(String accessToken, int testCaseId, int testRunId) {
        super("/api/test/_post/rq.json", "api/test/_post/rs.json", "api/test.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("Authorization=Bearer " + accessToken);
        addProperty("testCaseId", testCaseId);
        addProperty("testRunId", testRunId);
        addProperty("ciTestId", UUID.randomUUID());
    }
}
