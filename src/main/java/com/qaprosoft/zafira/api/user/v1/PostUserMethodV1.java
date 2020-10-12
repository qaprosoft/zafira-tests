package com.qaprosoft.zafira.api.user.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;
import org.apache.commons.lang3.RandomStringUtils;

public class PostUserMethodV1 extends ZafiraBaseApiMethodWithAuth {
    public PostUserMethodV1(String username) {
        super("api/user/v1/_post/rq.json", "api/user/v1/_post/rs.json", "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        addProperty("username", username);
        addProperty("email", "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com"));
        setHeaders("x-zbr-tenant=automation");
    }
}
