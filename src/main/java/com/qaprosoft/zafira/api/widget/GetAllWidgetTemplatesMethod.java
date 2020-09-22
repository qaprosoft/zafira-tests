package com.qaprosoft.zafira.api.widget;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetAllWidgetTemplatesMethod extends ZafiraBaseApiMethodWithAuth {
    public GetAllWidgetTemplatesMethod() {
        super(null, "api/widget/_get/rs_for_templates.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
