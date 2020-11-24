package com.qaprosoft.zafira.api.dashboard.widget;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.io.File;

public class PostDashboardByEmailMethod extends ZafiraBaseApiMethodWithAuth {
    public PostDashboardByEmailMethod(File file, String email) {
        super(null, null, "api/file_util.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setHeaders("Content-Type=multipart/form-data");
        request.multiPart("file", file, "image/png").multiPart("email", email,"application/json");
    }
}

