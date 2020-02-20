package com.qaprosoft.zafira.api.widget;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteWidgetMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteWidgetMethod(int widgetId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(widgetId));

    }
}
