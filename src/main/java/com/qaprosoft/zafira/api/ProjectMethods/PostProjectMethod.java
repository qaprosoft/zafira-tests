package com.qaprosoft.zafira.api.ProjectMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostProjectMethod extends ZafiraBaseApiMethodWithAuth {
    public PostProjectMethod(String projectName) {
        super("api/project/_post/rq.json", "api/project/_post/rs.json", "api/project.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("name", projectName);
    }
}
