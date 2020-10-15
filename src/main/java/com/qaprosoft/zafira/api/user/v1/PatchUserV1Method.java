package com.qaprosoft.zafira.api.user.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PatchUserV1Method extends ZafiraBaseApiMethodWithAuth {
    public PatchUserV1Method(int id, String path, String value) {
        super("api/user/v1/_patch/rq.json", null, "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("id", String.valueOf(id));
        addProperty("path", path);
        addProperty("value", value);
    }
}
