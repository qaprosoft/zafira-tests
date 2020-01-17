package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetAllLaunchersMethod extends AbstractApiMethodV2 {

    public GetAllLaunchersMethod(String accessToken) {
        super(null, "api/launcher/_get/rs_all.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
