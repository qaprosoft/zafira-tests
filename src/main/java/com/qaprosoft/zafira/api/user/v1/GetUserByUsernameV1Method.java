package com.qaprosoft.zafira.api.user.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;
import org.apache.commons.lang3.RandomStringUtils;

public class GetUserByUsernameV1Method extends ZafiraBaseApiMethodWithAuth {
    public GetUserByUsernameV1Method(String username) {
        super(null, "api/user/v1/_get/rs.json", "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("username", username);
    }
}
