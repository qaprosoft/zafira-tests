package com.qaprosoft.zafira.api.testRunSearchAttributesController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetFilterValuesMethod extends ZafiraBaseApiMethodWithAuth {

    public GetFilterValuesMethod() {
        super(null, "api/testRunSearchAttributesController/_get/rs_filter_values.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
