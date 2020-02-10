package com.qaprosoft.zafira.api.group;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostAddPermissionMethod extends ZafiraBaseApiMethodWithAuth {
    public PostAddPermissionMethod(int groupId, int permissionId) {
        super("api/group/_post/rq_for_add_permission.json", "api/group/_post/rs_for_add_permission.json",
                "api/group.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("id", groupId);
        addProperty("permissionId", permissionId);
    }
}
