package com.qaprosoft.zafira.api.assets;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.io.File;

public class DeleteAssetByKeyMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteAssetByKeyMethod(String key) {
        super(null, null, "api/file_util.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("FileType=USERS");
        setHeaders("Content-Type=multipart/form-data");
        replaceUrlPlaceholder("key", "key");
    }
}
