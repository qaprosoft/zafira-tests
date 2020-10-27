package com.qaprosoft.zafira.api.socialController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetZebrunnerTweetsMethod extends ZafiraBaseApiMethodWithAuth {
    public GetZebrunnerTweetsMethod() {
        super(null, "api/socialController/_get/rs_for_tweets.json", new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
    }
}
