package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.service.impl.AuthServiceApiIamImpl;

import java.util.Properties;

public class GetAllPermissionsMethodIAM extends ZafiraBaseApiMethodWithAuth {
    public GetAllPermissionsMethodIAM() {
        super(null, "api/permissionIAM/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url_IAM", APIContextManager.BASE_URL);
        setHeaders("x-zbr-tenant=automation");

    }
}
