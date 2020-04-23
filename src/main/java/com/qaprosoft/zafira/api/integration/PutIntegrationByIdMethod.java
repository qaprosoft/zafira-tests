package com.qaprosoft.zafira.api.integration;

import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.util.CryptoUtil;

public class PutIntegrationByIdMethod extends ZafiraBaseApiMethodWithAuth {
    public PutIntegrationByIdMethod(String rqPath, int integrationId, Boolean enabledType) {
        super(rqPath, "api/integration/_put/rs.json", "api/integration.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("integrationId", String.valueOf(integrationId));
        addProperty("enabled", String.valueOf(enabledType));
        addProperty("slackWebHook", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.SLACK_WEBHOOK_KEY)));
    }
}
