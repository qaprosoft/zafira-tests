package com.qaprosoft.zafira.api.UserMethods;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Properties;

public class PutCreateUserMethod extends ZafiraBaseApiMethodWithAuth {
    public PutCreateUserMethod(String username) {
        super("api/user/_put/rq.json", "api/user/_put/rs.json", "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        addProperty("username", username);
        addProperty("email", "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com"));
    }
}
