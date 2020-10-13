package com.qaprosoft.zafira.api.user.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;
import org.apache.commons.lang3.RandomStringUtils;

public class PostNewUserPasswordMethodV1 extends ZafiraBaseApiMethodWithAuth {
    public PostNewUserPasswordMethodV1(int id, String oldPassword, String newPassword) {
        super("api/user/v1/_post/rq_for_new_password.json", null, "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("id", String.valueOf(id));
        addProperty("oldPassword", oldPassword);
        addProperty("newPassword", newPassword);
    }
}
