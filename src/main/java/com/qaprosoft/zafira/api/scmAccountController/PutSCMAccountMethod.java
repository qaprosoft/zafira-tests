package com.qaprosoft.zafira.api.scmAccountController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class PutSCMAccountMethod extends ZafiraBaseApiMethodWithAuth {
    public PutSCMAccountMethod(int id) {
        super("api/scmAccountController/_put/rq.json", "api/scmAccountController/_put/rs.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("id", String.valueOf(id));
    }
}
