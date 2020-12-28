package com.qaprosoft.zafira.api.assets;

import java.io.File;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostAssetMethod extends ZafiraBaseApiMethodWithAuth {
    public PostAssetMethod(String type, File uploadFile) {
        super(null, "api/file_util/_post/rs.json", "api/file_util.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("type", type);
        setHeaders("FileType=USERS");
        setHeaders("Content-Type=multipart/form-data");
        request.multiPart("file", uploadFile, "image/jpeg");
    }
}

