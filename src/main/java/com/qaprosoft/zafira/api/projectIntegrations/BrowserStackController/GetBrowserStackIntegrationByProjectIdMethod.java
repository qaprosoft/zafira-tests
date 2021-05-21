package com.qaprosoft.zafira.api.projectIntegrations.BrowserStackController;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.util.Base64Util;
import com.qaprosoft.zafira.util.CryptoUtil;

import java.io.UnsupportedEncodingException;

@ResponseTemplatePath(path = "api/projectIntegrations/browserStack/_get/rs.json")
@Endpoint(url = "${api_url}/v1/integrations/browser-stack?projectId=${projectId}&onlyActive=false&decryptValues=true",
        methodType = HttpMethodType.GET)
public class GetBrowserStackIntegrationByProjectIdMethod extends AbstractApiMethodV2 {
    public GetBrowserStackIntegrationByProjectIdMethod(int projectId) throws UnsupportedEncodingException {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));

        String basicAuth = Base64Util
                .encodeString(CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.BASIC_AUTH_USERNAME_KEY))
                        + ":" + CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.BASIC_AUTTH_FOR_INTEGRATION_PASSWORD_KEY)));

        setHeaders("Authorization=Basic " + basicAuth);
    }
}
