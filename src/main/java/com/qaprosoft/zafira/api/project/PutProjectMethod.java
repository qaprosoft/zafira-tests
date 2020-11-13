package com.qaprosoft.zafira.api.project;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PutProjectMethod extends ZafiraBaseApiMethodWithAuth {
    public PutProjectMethod(String projectName, int id) {
        super("api/project/_put/rq.json", "api/project/_put/rs.json", "api/project.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("name", projectName);
        addProperty("id", String.valueOf(id));
    }
}
