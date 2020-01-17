package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostScanLaucherMethod extends AbstractApiMethodV2 {

    public PostScanLaucherMethod(String accessToken, int scmAccountId) {
        super("api/launcher/_post/rq_for_scan_laucher.json", "api/launcher/_post/rs_for_scan_launcher.json",
                "api/launcher.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("scmAccountId", scmAccountId);
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
