package com.qaprosoft.zafira.api.integration;

import java.util.logging.Logger;
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
            Logger.getAnonymousLogger().info(String.format("integrationName = %s", integrationName));
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
        case IntegrationConstant.AMAZON:
            addProperty("amazonAccessKey", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.AMAZON_ACCESS_KEY)));
            addProperty("amazonSecretKey", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.AMAZON_SECTER_KEY)));
            break;
        case IntegrationConstant.AMAZON_NEGATIVE:
            addProperty("amazonSecretKey", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.AMAZON_SECTER_KEY)));
            break;
        case IntegrationConstant.BROWSERSTACK:
            addProperty("browserstackAccessKey", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.BROWSERSTACK_ACCESS_KEY)));
            break;
        case IntegrationConstant.EMAIL:
        case IntegrationConstant.EMAIL_NEGATIVE:
            addProperty("emailPassword", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.EMAIL_PASSWORD_KEY)));
            break;
        case IntegrationConstant.JIRA:
            addProperty("jiraPassword", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.JIRA_PASSWORD_KEY)));
            break;
        case IntegrationConstant.MCOUD:
        case IntegrationConstant.MCLOUD_NEGATIVE:
            addProperty("mcloudPassword", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.MCLOUD_PASSWORD_KEY)));
            break;
        case IntegrationConstant.RABBITMQ:
        case IntegrationConstant.RABBITMQ_NEGATIVE:
            addProperty("rabbitmqPassword", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.RABBITMQ_PASSWORD_KEY)));
            break;
        case IntegrationConstant.SAUCELABS:
            addProperty("saucelabsPassword", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.SAUCELABS_PASSWORD_KEY)));
            break;
        case IntegrationConstant.ZEBRUNNER:
        case IntegrationConstant.ZEBRUNNER_NEGATIVE:
            addProperty("zebrunnerPassword", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.ZEBRUNNER_PASSWORD_KEY)));
            break;
        }
    }
}
