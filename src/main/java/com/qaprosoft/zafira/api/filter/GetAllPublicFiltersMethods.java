package com.qaprosoft.zafira.api.filter;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetAllPublicFiltersMethods extends ZafiraBaseApiMethodWithAuth {
    public GetAllPublicFiltersMethods() {
        super(null, "api/filter/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
