package com.qaprosoft.zafira.api.LauncherMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostJobByLauncherMethod extends ZafiraBaseApiMethodWithAuth {

    public PostJobByLauncherMethod(int scmAccountId) {
        super("api/launcher/_post/rq_for_job_by_laucher.json", null, "api/launcher.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("id", scmAccountId);
    }

}
