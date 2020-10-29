package com.qaprosoft.zafira.api.jobController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostJobMethod extends ZafiraBaseApiMethodWithAuth {

    public PostJobMethod() {
        super("api/job/_post/rq.json", "api/job/_post/rs.json", "api/job.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
