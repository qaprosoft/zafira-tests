package com.qaprosoft.zafira.api.dashboard.widget;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class PostWidgetToDashboardMethod extends ZafiraBaseApiMethodWithAuth {
    public PostWidgetToDashboardMethod(String body, int dashboardId) {
        super(null, "api/dashboard/widget/_post/rs.json",
                "api/widget.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(dashboardId));
        getRequest().body(body);
    }
}
