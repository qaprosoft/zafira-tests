package com.qaprosoft.zafira.api.projectIntegrations.jiraWithXray;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.util.CryptoUtil;
import org.apache.commons.lang3.RandomStringUtils;

@RequestTemplatePath(path = "api/projectIntegrations/jira/_post/rq.json")
@ResponseTemplatePath(path = "api/projectIntegrations/jira/_post/rs.json")
@Endpoint(url = "${api_url}/v1/integrations/jira/connectivity-checks?projectId=${projectId}", methodType = HttpMethodType.POST)
public class PostCheckConnectionJiraIntegrationMethod extends ZafiraBaseApiMethodWithAuth {
    public PostCheckConnectionJiraIntegrationMethod(int projectId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));

        addProperty("username", R.TESTDATA.get(ConfigConstant.JIRA_USERNAME));
        addProperty("token", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.JIRA_TOKEN)));
        addProperty("url", R.TESTDATA.get(ConfigConstant.JIRA_URL));
        addProperty("reachable", String.valueOf(true));
    }
}
