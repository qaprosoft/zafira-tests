package com.qaprosoft.zafira.api.ProjectMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteProjectByIdMethod extends ZafiraBaseApiMethodWithAuth {
    public DeleteProjectByIdMethod(int projectId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(projectId));
    }
}
