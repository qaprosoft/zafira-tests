package com.qaprosoft.zafira.api.scmAccountController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteSCMAccountMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteSCMAccountMethod(int id) {
        super(null, null, new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(id));
    }
}
