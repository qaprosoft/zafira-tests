package com.qaprosoft.zafira.api.authIAM;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.carina.core.foundation.api.annotation.HideRequestBodyPartsInLogs;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.zafira.manager.APIContextManager;
@HideRequestBodyPartsInLogs(paths = {"password", "username"})
@RequestTemplatePath(path ="api/authIAM/_post/rq_for_generate.json")
public class PostGenerateAuthTokenMethodIAM extends AbstractApiMethodV2 {
    public PostGenerateAuthTokenMethodIAM(String username, String password) {
        super("api/authIAM/_post/rq_for_generate.json", "api/authIAM/_post/rs_for_generate.json",
                "api/auth.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        addProperty("password", password);
        addProperty("username", username);
    }
}
