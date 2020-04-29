package com.qaprosoft.zafira.api.integration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.IntegrationConstant;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.util.CryptoUtil;

public class PutIntegrationByIdMethod extends ZafiraBaseApiMethodWithAuth {

    public PutIntegrationByIdMethod(String rqPath, int integrationId, Boolean enabledType) {
        super(rqPath, "api/integration/_put/rs.json", "api/integration.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("integrationId", String.valueOf(integrationId));
        addProperty("enabled", String.valueOf(enabledType));
        addValidProperty(rqPath);

    }

    private String getIntegrationName(String rqPath) {
        Pattern pattern = Pattern.compile("([A-Z])\\w+");
        Matcher matcher = pattern.matcher(rqPath);
        String integrationName = "";
        if (matcher.find()) {
            integrationName = matcher.group();
            LOGGER.info(String.format("integrationName = %s", integrationName));
        }
        return integrationName;
    }

    private void addValidProperty(String rqPath) {
        switch (getIntegrationName(rqPath)) {
        case IntegrationConstant.SLACK:
            addProperty("slackWebHook", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.SLACK_WEBHOOK_KEY)));
            break;
        case IntegrationConstant.LAMBDATEST:
            addProperty("lambdatestToken", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.LAMBDATEST_TOKEN_KEY)));
            break;
        }
    }
}
