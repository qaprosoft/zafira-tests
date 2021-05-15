package com.qaprosoft.zafira.api.widget;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostWidgetMethod extends ZafiraBaseApiMethodWithAuth {
    public PostWidgetMethod(String widgetName) {
        super("api/widget/_post/rq.json", "api/widget/_post/rs.json", "api/widget.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("title", widgetName);
    }

    public PostWidgetMethod(String widgetName, String token) {
        super("api/widget/_post/rq.json", "api/widget/_post/rs.json", "api/widget.properties", token);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("title", widgetName);
    }
}
