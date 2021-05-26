package com.qaprosoft.zafira.api.projectIntegrations.SauceLabsController;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.util.CryptoUtil;

@RequestTemplatePath(path = "api/projectIntegrations/sauceLabs/_put/rq.json")
@ResponseTemplatePath(path = "api/projectIntegrations/sauceLabs/_put/rs.json")
@Endpoint(url = "${api_url}/v1/integrations/sauce-labs?projectId=${projectId}", methodType = HttpMethodType.PUT)
public class PutSaveSauceLabsIntegrationMethod extends ZafiraBaseApiMethodWithAuth {
    public PutSaveSauceLabsIntegrationMethod(int projectId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));

        addProperty("username", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.SAUCE_LABS_USERNAME)));
        addProperty("accessKey", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.SAUCE_LABS_ACCESS_KEY)));
        addProperty("hubUrl", R.TESTDATA.get(ConfigConstant.SAUCE_LABS_URL));
        addProperty("enabled", true);
    }

    public PutSaveSauceLabsIntegrationMethod(int projectId, String token) {
        super(token);
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
        addProperty("username", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.SAUCE_LABS_USERNAME)));
        addProperty("accessKey", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.SAUCE_LABS_ACCESS_KEY)));
        addProperty("hubUrl", R.TESTDATA.get(ConfigConstant.SAUCE_LABS_URL));
        addProperty("enabled", true);
    }
}
