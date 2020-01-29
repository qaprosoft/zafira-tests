package com.qaprosoft.zafira.api.UserMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteUserFromGroupMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteUserFromGroupMethod(int groupId, int userId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("groupId", String.valueOf(groupId));
        replaceUrlPlaceholder("userId", String.valueOf(userId));
    }
}
