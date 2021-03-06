package com.qaprosoft.zafira.api.projectIntegrations.LambdaTestController;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.util.CryptoUtil;

@RequestTemplatePath(path = "api/projectIntegrations/lambdaTest/_post/rq.json")
@ResponseTemplatePath(path = "api/projectIntegrations/lambdaTest/_post/rs.json")
@Endpoint(url = "${api_url}/v1/integrations/lambda-test/connectivity-checks?projectId=${projectId}", methodType = HttpMethodType.POST)
public class PostCheckConnectionLambdaTestIntegrationMethod extends ZafiraBaseApiMethodWithAuth {
    public PostCheckConnectionLambdaTestIntegrationMethod(int projectId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));

        addProperty("username", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.LAMBDA_TEST_USERNAME)));
        addProperty("accessKey", CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.LAMBDA_TEST_ACCESS_KEY)));
        addProperty("hubUrl", R.TESTDATA.get(ConfigConstant.LAMBDA_TEST_URL));
        addProperty("reachable", String.valueOf(true));
    }
}
