package com.qaprosoft.zafira.api.GroupMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteGroupByIdMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteGroupByIdMethod(int groupId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(groupId));
    }
}
