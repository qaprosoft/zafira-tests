package com.qaprosoft.zafira.api.filter;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetFilterBuilderMethod extends ZafiraBaseApiMethodWithAuth {
    public GetFilterBuilderMethod(String subjectName) {
        super(null, "api/filter/_get/rs_for_filter_builder.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("name", subjectName);
    }
}
