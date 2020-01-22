package com.qaprosoft.zafira.api.LauncherMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetAllLaunchersMethod extends ZafiraBaseApiMethodWithAuth {

    public GetAllLaunchersMethod() {
        super(null, "api/launcher/_get/rs_all.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
