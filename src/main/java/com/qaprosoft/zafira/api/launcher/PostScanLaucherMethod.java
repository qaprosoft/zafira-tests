package com.qaprosoft.zafira.api.launcher;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostScanLaucherMethod extends ZafiraBaseApiMethodWithAuth {

    public PostScanLaucherMethod(int scmAccountId) {
        super("api/launcher/_post/rq_for_scan_laucher.json", "api/launcher/_post/rs_for_scan_launcher.json",
                "api/launcher.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("automationServerId", String.valueOf(APIContextManager.AUTHOMATION_SERVER_ID_VALUE));
        addProperty("scmAccountId", String.valueOf(scmAccountId));
    }
}
