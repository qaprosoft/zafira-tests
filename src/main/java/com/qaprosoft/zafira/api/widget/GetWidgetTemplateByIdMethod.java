package com.qaprosoft.zafira.api.widget;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetWidgetTemplateByIdMethod extends ZafiraBaseApiMethodWithAuth {
    public GetWidgetTemplateByIdMethod(int templateId) {
        super(null, "api/widget/_get/rs_for_templates_by_id.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(templateId));
    }
}
