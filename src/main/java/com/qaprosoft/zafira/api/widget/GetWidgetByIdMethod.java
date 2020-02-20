package com.qaprosoft.zafira.api.widget;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetWidgetByIdMethod extends ZafiraBaseApiMethodWithAuth {
    public GetWidgetByIdMethod(int widgetId) {
        super(null, "api/widget/_get/rs_by_id.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(widgetId));
    }
}
