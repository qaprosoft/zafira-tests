package com.qaprosoft.zafira.api.filter;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteFilterMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteFilterMethod(int filterId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(filterId));
    }
}
