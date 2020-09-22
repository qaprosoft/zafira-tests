package com.qaprosoft.zafira.api.widget;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutWidgetMethod extends ZafiraBaseApiMethodWithAuth {
    public PutWidgetMethod(int widgetId, String widgetName) {
        super("api/widget/_put/rq.json", "api/widget/_put/rs.json", "api/widget.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("id", String.valueOf(widgetId));
        addProperty("title", widgetName);
    }
}
