package com.qaprosoft.zafira.api.scmAccountController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetGithubConfigMethod extends ZafiraBaseApiMethodWithAuth {
    public GetGithubConfigMethod() {
        super(null, "api/scmAccountController/_get/rs_for_github_config.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
