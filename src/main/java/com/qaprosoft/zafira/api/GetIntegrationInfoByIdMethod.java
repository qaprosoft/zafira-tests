package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.enums.IntegrationGroupType;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetIntegrationInfoByIdMethod extends AbstractApiMethodV2 {
    public GetIntegrationInfoByIdMethod(String accessToken, int id, IntegrationGroupType integrationGroup) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(id));
        replaceUrlPlaceholder("integrationGroup", integrationGroup.getGroup());
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
