package com.qaprosoft.zafira.api.scmAccountController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetAllSCMAccountsMethod extends ZafiraBaseApiMethodWithAuth {
    public GetAllSCMAccountsMethod() {
        super(null, "api/scmAccountController/_get/rs_for_all.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
