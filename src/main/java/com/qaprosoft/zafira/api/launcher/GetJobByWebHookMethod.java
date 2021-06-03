package com.qaprosoft.zafira.api.launcher;

import java.util.Properties;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;

public class GetJobByWebHookMethod extends ZafiraBaseApiMethodWithAuth {

    public GetJobByWebHookMethod(String webHookUrl) {
        super(null, null, (Properties) null);
        replaceUrlPlaceholder("base_api_url", webHookUrl);
    }
}
