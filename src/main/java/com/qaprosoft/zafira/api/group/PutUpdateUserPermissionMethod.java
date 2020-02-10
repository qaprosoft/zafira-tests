package com.qaprosoft.zafira.api.group;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutUpdateUserPermissionMethod extends ZafiraBaseApiMethodWithAuth {
    public PutUpdateUserPermissionMethod(int groupId, int permissionId, String groupName) {
        super("api/group/_post/rq_for_update.json", "api/group/_post/rs_for_update.json",
                "api/group.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("id", groupId);
        addProperty("name", groupName);
        addProperty("permissionId", permissionId);
    }
}
