package com.qaprosoft.zafira.api.launcher;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PatchLauncherMethod extends ZafiraBaseApiMethodWithAuth {
    public PatchLauncherMethod(int id, Boolean value) {
        super("api/launcher/_patch/rq.json", "api/launcher/_patch/rs.json", "api/launcher.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(id));
        addProperty("value",String.valueOf(value));
    }
}
