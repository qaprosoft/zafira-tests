package com.qaprosoft.zafira.api.groupIAM;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.service.impl.AuthServiceApiIamImpl;

public class PutUpdateUserPermissionMethodIAM extends ZafiraBaseApiMethodWithAuth {
    public PutUpdateUserPermissionMethodIAM ( int groupId, String permissionName, String groupName) {
        super("api/groupIAM/_put/rq.json", "api/groupIAM/_put/rs.json",
                "api/group.properties");
        replaceUrlPlaceholder("base_api_url_IAM", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("id", String.valueOf(groupId));
        addProperty("permission", permissionName);
        addProperty("name", groupName);
        setHeaders("x-zbr-tenant=automation");
    }
}
