package com.qaprosoft.zafira.api.failureTagAssignment;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetStacktraceLabelsMethod extends ZafiraBaseApiMethodWithAuth {

    public GetStacktraceLabelsMethod() {
        super(null, null, new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
