package com.qaprosoft.zafira.api;

import java.io.File;

import com.qaprosoft.zafira.manager.APIContextManager;

public class PostUploadFileMethod extends ZafiraBaseApiMethodWithAuth {
    public PostUploadFileMethod(File uploadFile) {
        super(null, "api/file_util/_post/rs.json", "api/file_util.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("FileType=USERS");
        setHeaders("Content-Type=multipart/form-data");
        request.multiPart("file", uploadFile, "image/jpeg");
    }
}

