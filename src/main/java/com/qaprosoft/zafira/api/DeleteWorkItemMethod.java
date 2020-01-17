package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteWorkItemMethod extends AbstractApiMethodV2 {
    public DeleteWorkItemMethod(String accessToken, int testId, int workItemId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("testId", String.valueOf(testId));
        replaceUrlPlaceholder("workItemId", String.valueOf(workItemId));
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
