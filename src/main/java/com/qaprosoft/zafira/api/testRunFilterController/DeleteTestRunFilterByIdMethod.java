package com.qaprosoft.zafira.api.testRunFilterController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteTestRunFilterByIdMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteTestRunFilterByIdMethod(int id) {
        super(null, null, new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(id));
    }
}
