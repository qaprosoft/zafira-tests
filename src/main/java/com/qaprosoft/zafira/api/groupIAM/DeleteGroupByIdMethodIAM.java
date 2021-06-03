package com.qaprosoft.zafira.api.groupIAM;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class DeleteGroupByIdMethodIAM extends ZafiraBaseApiMethodWithAuth {
    public DeleteGroupByIdMethodIAM (int groupId) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url_IAM", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("id", String.valueOf(groupId));
        setHeaders("x-zbr-tenant=automation");
    }
}
