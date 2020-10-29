package com.qaprosoft.zafira.api.jobController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostJobByJenkinsJobURLMethod extends ZafiraBaseApiMethodWithAuth {

    public PostJobByJenkinsJobURLMethod() {
        super("api/job/_post/rq_by_job_url.json", "api/job/_post/rs_by_job_url.json", "api/job.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
