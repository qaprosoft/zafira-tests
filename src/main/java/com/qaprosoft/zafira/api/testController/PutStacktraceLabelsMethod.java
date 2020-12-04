package com.qaprosoft.zafira.api.testController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class PutStacktraceLabelsMethod extends ZafiraBaseApiMethodWithAuth {

    public PutStacktraceLabelsMethod(int testId, String stacktraceLabelsName) {
        super("api/test/_put/rq_for_stacktrace_labels.json", "api/test/_put/rs_for_stacktrace_labels.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(testId));
        addProperty("name", stacktraceLabelsName);
    }
}
