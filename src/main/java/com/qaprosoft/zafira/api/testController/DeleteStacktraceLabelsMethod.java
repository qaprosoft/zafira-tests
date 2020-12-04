package com.qaprosoft.zafira.api.testController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteStacktraceLabelsMethod extends ZafiraBaseApiMethodWithAuth {

    public DeleteStacktraceLabelsMethod(int testId) {
        super(null, null, new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testId));
    }
}
