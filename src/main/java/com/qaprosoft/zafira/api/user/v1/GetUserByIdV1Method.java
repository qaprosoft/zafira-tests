package com.qaprosoft.zafira.api.user.v1;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

public class GetUserByIdV1Method extends ZafiraBaseApiMethodWithAuth {
    public GetUserByIdV1Method(int id) {
        super(null, "api/user/v1/_get/rs.json", "api/user.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("id", String.valueOf(id));

    }
}
