package com.qaprosoft.zafira.api.testController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetJiraIssueByItsIdMethod extends ZafiraBaseApiMethodWithAuth {

    public GetJiraIssueByItsIdMethod(String issue) {
        super(null, "api/test/_get/rs.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("issue", issue);
    }
}
