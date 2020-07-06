package com.qaprosoft.zafira.api.groupIAM;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.service.impl.AuthServiceApiIamImpl;

import java.util.Properties;

public class GetGroupByIdMethodIAM extends ZafiraBaseApiMethodWithAuth {
    public GetGroupByIdMethodIAM (int groupId) {
        super(null, "api/groupIAM/_get/rs_by_id.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url_IAM", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("groupId", String.valueOf(groupId));
        setHeaders("x-zbr-tenant=automation");
    }
}
