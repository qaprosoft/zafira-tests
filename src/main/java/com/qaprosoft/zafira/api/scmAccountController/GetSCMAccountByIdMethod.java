package com.qaprosoft.zafira.api.scmAccountController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetSCMAccountByIdMethod extends ZafiraBaseApiMethodWithAuth {
    public GetSCMAccountByIdMethod(int id) {
        super(null, "api/scmAccountController/_get/rs_by_id.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(id));
    }
}
