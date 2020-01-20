package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.manager.APIContextManager;

public class PostScanLaucherMethod extends ZafiraBaseApiMethodWithAuth {

    public PostScanLaucherMethod(int scmAccountId) {
        super("api/launcher/_post/rq_for_scan_laucher.json", "api/launcher/_post/rs_for_scan_launcher.json",
                "api/launcher.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("scmAccountId", scmAccountId);
    }
}
