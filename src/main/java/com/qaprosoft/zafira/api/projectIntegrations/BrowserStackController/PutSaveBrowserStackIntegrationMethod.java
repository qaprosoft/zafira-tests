package com.qaprosoft.zafira.api.projectIntegrations.BrowserStackController;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.util.CryptoUtil;

@RequestTemplatePath(path = "api/projectIntegrations/browserStack/_put/rq.json")
@ResponseTemplatePath(path = "api/projectIntegrations/browserStack/_put/rs.json")
@Endpoint(url = "${api_url}/v1/integrations/browser-stack?projectId=${projectId}", methodType = HttpMethodType.PUT)
public class PutSaveBrowserStackIntegrationMethod extends ZafiraBaseApiMethodWithAuth {
    public PutSaveBrowserStackIntegrationMethod(int projectId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));

        addProperty("username", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.BROWSERSTACK_USERNAME)));
        addProperty("accessKey", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.BROWSERSTACK_ACCESSKEY)));
        addProperty("hubUrl", R.TESTDATA.get(ConfigConstant.BROWSERSTACK_URL));
        addProperty("enabled", true);
    }

    public PutSaveBrowserStackIntegrationMethod(int projectId, String token) {
        super(token);
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
        addProperty("hubUrl", R.TESTDATA.get(ConfigConstant.BROWSERSTACK_URL));
        addProperty("username", R.TESTDATA.get(ConfigConstant.BROWSERSTACK_USERNAME));
        addProperty("accessKey", R.TESTDATA.get(ConfigConstant.BROWSERSTACK_ACCESSKEY));
        addProperty("enabled", true);
    }
}
