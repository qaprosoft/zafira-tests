package com.qaprosoft.zafira.api.notificationController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetSlackNotificationFinishTestRunByCiRunIdMethod extends ZafiraBaseApiMethodWithAuth {

    public GetSlackNotificationFinishTestRunByCiRunIdMethod(String ciRunId) {
        super(null, null, new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("ciRunId", String.valueOf(ciRunId));
        replaceUrlPlaceholder("channel", ConfigConstant.SLACK_CHANNEL_KEY);
    }
}
