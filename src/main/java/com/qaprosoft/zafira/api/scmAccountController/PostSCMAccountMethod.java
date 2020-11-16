package com.qaprosoft.zafira.api.scmAccountController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class PostSCMAccountMethod extends ZafiraBaseApiMethodWithAuth {
    public PostSCMAccountMethod() {
        super("api/scmAccountController/_post/rq.json", "api/scmAccountController/_post/rs.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
