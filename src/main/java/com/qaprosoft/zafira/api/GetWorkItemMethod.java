package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetWorkItemMethod extends AbstractApiMethodV2 {

    public GetWorkItemMethod(String accessToken, int testId, String workItemType) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testId));
        replaceUrlPlaceholder("type", workItemType);
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
