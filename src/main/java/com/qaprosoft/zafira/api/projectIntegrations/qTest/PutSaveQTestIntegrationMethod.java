package com.qaprosoft.zafira.api.projectIntegrations.qTest;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.util.CryptoUtil;
import org.apache.commons.lang3.RandomStringUtils;

@RequestTemplatePath(path = "api/projectIntegrations/qtest/_put/rq.json")
@ResponseTemplatePath(path = "api/projectIntegrations/qtest/_put/rs.json")
@Endpoint(url = "${api_url}/v1/integrations/qtest?projectId=${projectId}", methodType = HttpMethodType.PUT)
public class PutSaveQTestIntegrationMethod extends ZafiraBaseApiMethodWithAuth {
    public PutSaveQTestIntegrationMethod(int projectId) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));

        addProperty("token", "QTest_accessKey".concat(RandomStringUtils.randomAlphabetic(3)));
        addProperty("url", R.TESTDATA.get(ConfigConstant.QTEST_TEST_URL));
        addProperty("encrypted", String.valueOf(false));
    }

    public PutSaveQTestIntegrationMethod(int projectId, String token) {
        super(token);
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));

        addProperty("token", "QTest_accessKey".concat(RandomStringUtils.randomAlphabetic(3)));
        addProperty("url", R.TESTDATA.get(ConfigConstant.QTEST_TEST_URL));
        addProperty("encrypted", String.valueOf(false));
    }
}
