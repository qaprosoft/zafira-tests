package com.qaprosoft.zafira.api.settingsController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class PutUpdateSettingsMethod extends ZafiraBaseApiMethodWithAuth {

    public PutUpdateSettingsMethod(String newName) {
        super("api/settingsController/_put/rq_for_update.json",
                "api/settingsController/_put/rs_for_update.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("name", newName);
    }
}
