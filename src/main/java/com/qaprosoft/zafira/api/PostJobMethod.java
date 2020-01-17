package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostJobMethod extends AbstractApiMethodV2 {

    public PostJobMethod(String accessToken) {
        super("api/job/_post/rq.json", "api/job/_post/rs.json", "api/job.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
