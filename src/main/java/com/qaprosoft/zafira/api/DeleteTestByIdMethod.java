package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteTestByIdMethod extends AbstractApiMethodV2 {

    public DeleteTestByIdMethod(String accessToken, int testId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testId));
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
