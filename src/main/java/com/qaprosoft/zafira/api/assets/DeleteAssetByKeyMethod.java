package com.qaprosoft.zafira.api.assets;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class DeleteAssetByKeyMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteAssetByKeyMethod(String key) {
        super(null, null, "api/file_util.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("key", key);
    }
}
