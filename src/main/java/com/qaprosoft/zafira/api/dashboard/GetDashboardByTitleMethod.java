package com.qaprosoft.zafira.api.dashboard;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetDashboardByTitleMethod extends ZafiraBaseApiMethodWithAuth {
    public GetDashboardByTitleMethod(String title) {
        super(null, "api/dashboard/_get/rs_by_title.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("title", title);
    }
}
