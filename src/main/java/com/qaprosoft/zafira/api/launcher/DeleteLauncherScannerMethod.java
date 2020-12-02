package com.qaprosoft.zafira.api.launcher;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteLauncherScannerMethod extends ZafiraBaseApiMethodWithAuth {

    public DeleteLauncherScannerMethod(int scmAccountId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("scmAccountId", String.valueOf(scmAccountId));
        replaceUrlPlaceholder("automationServerId", String.valueOf(APIContextManager.AUTHOMATION_SERVER_ID_VALUE));
    }
}
