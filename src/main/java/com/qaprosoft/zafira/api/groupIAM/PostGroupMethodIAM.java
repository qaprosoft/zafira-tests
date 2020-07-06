package com.qaprosoft.zafira.api.groupIAM;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.service.impl.AuthServiceApiIamImpl;

public class PostGroupMethodIAM extends ZafiraBaseApiMethodWithAuth {
    public PostGroupMethodIAM (String groupName) {
        super("api/groupIAM/_post/rq.json", "api/groupIAM/_post/rs.json", "api/group.properties");
        replaceUrlPlaceholder("base_api_url_IAM", APIContextManager.BASE_URL);
        addProperty("name", groupName);
        setHeaders("x-zbr-tenant=automation");
    }
}
