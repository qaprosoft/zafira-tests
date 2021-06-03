package com.qaprosoft.zafira.api.user.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteUserFromGroupV1Method extends ZafiraBaseApiMethodWithAuth {
    public DeleteUserFromGroupV1Method(int groupId, int userId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("groupId", String.valueOf(groupId));
        replaceUrlPlaceholder("userId", String.valueOf(userId));
    }
}
