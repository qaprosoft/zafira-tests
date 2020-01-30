package com.qaprosoft.zafira.api.GroupMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetGroupByIdMethod extends ZafiraBaseApiMethodWithAuth {
    public GetGroupByIdMethod(int groupId) {
        super(null, "api/group/_get/rs_by_id.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("groupId", String.valueOf(groupId));
    }
}
