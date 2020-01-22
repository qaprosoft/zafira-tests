package com.qaprosoft.zafira.api.LauncherMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteLauncherScannerMethod extends ZafiraBaseApiMethodWithAuth {

    public DeleteLauncherScannerMethod(String buildNumber, int scmAccountId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("buildNumber", buildNumber);
        replaceUrlPlaceholder("scmAccountId", String.valueOf(scmAccountId));
    }
}
