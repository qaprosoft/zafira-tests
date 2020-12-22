package com.qaprosoft.zafira.api.testRunFilterController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class PatchTestRunFilterMethod extends ZafiraBaseApiMethodWithAuth {

    public PatchTestRunFilterMethod(int id, String path, Object value) {
        super("api/testRunFilter/_patch/rq.json", null, new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(id));
        addProperty("path", path);
        addProperty("value", value);
    }
}
