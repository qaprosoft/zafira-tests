package com.qaprosoft.zafira.api.launcher;

import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostLauncherMethod extends ZafiraBaseApiMethodWithAuth {
    public PostLauncherMethod(int jobId, int scmAccountId) {
        super("api/launcher/_post/rq.json", "api/launcher/_post/rs.json", "api/launcher.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("automationServerId", String.valueOf(R.TESTDATA.get(ConfigConstant.AUTHOMATION_SERVER_KEY)));
        addProperty("id", String.valueOf(scmAccountId));
        addProperty("jobId", String.valueOf(jobId));
    }
}
