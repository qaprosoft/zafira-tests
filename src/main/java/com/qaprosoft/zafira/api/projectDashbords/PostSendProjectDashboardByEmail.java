package com.qaprosoft.zafira.api.projectDashbords;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.io.File;


@Endpoint(url = "${api_url}/api/project-dashboards/${dashboardId}/email", methodType = HttpMethodType.POST)
public class PostSendProjectDashboardByEmail extends ZafiraBaseApiMethodWithAuth {
    public PostSendProjectDashboardByEmail(int dashboardId, File file, File email) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("dashboardId", String.valueOf(dashboardId));
        setHeaders("Content-Type=multipart/form-data");
        request.multiPart("file", file, "image/png").multiPart("email", email,"application/json");
    }
}
