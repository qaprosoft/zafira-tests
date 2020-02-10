package com.qaprosoft.zafira.api.project;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetProjectByName extends ZafiraBaseApiMethodWithAuth {
    public GetProjectByName(String projectName) {
        super(null, "api/project/_get/rs_for_get_by_name.json", (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("name", projectName);
    }
}