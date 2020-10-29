package com.qaprosoft.zafira.api.jobController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetAllJobsMethod extends ZafiraBaseApiMethodWithAuth {
    public GetAllJobsMethod(){
        super (null,"api/job/_get/rs.json",(Properties) null);
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}


