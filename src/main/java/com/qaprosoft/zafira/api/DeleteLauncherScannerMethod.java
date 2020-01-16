package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteLauncherScannerMethod extends AbstractApiMethodV2 {

    public DeleteLauncherScannerMethod(String accessToken, String buildNumber, int scmAccountId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("buildNumber", buildNumber);
        replaceUrlPlaceholder("scmAccountId", String.valueOf(scmAccountId));
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
