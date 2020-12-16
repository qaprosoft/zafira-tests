package com.qaprosoft.zafira.api.notificationController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetSlackNotificationReviewedByTestRunIdMethod extends ZafiraBaseApiMethodWithAuth {

    public GetSlackNotificationReviewedByTestRunIdMethod(int id) {
        super(null, null, new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(id));
    }
}
