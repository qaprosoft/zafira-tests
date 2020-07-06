package com.qaprosoft.zafira.api.groupIAM;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.service.impl.AuthServiceAPIImpl;
import com.qaprosoft.zafira.service.impl.AuthServiceApiIamImpl;

import java.util.Properties;

public class GetAllGroupsMethodIAM extends ZafiraBaseApiMethodWithAuth {
    public GetAllGroupsMethodIAM() {
        super(null, "api/groupIAM/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url_IAM", APIContextManager.BASE_URL);
        setHeaders("x-zbr-tenant=automation");
    }
}
