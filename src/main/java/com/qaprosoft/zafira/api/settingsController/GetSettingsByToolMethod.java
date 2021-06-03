package com.qaprosoft.zafira.api.settingsController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetSettingsByToolMethod extends ZafiraBaseApiMethodWithAuth {

    public GetSettingsByToolMethod(String tool) {
        super(null, "api/settingsController/_get/rs_for_settings_by_tool.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("tool", tool);
    }
}
