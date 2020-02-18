package com.qaprosoft.zafira.api.filter;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutFilterMethod extends ZafiraBaseApiMethodWithAuth {

    public PutFilterMethod(int filterId, String filterName) {
        super("api/filter/_put/rq.json", "api/filter/_put/rs.json", "api/filter.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("id", String.valueOf(filterId));
        addProperty("name", filterName);
    }
}
