package com.qaprosoft.zafira.api.user.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteUserByIdV1Method extends ZafiraBaseApiMethodWithAuth {
    public DeleteUserByIdV1Method( int userId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("userId", String.valueOf(userId));
    }
}
