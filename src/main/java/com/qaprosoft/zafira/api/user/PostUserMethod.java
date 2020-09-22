package com.qaprosoft.zafira.api.user;

import org.apache.commons.lang3.RandomStringUtils;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class PostUserMethod extends ZafiraBaseApiMethodWithAuth {
    public PostUserMethod(String username) {
        super("api/user/_put/rq.json", "api/user/_put/rs.json", "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("username", username);
        addProperty("email", "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com"));
    }
}
