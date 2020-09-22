package com.qaprosoft.zafira.api.authIAM;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetAccessTokenMethod extends ZafiraBaseApiMethodWithAuth {
    public GetAccessTokenMethod() {
        super(null, "api/authIAM/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);

    }
}
