package com.qaprosoft.zafira.api.project;

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

@ResponseTemplatePath(path = "api/projectsV1/_get/rs_for_user_assignments.json")
@Endpoint(url = "${projects_url}/v1/user-assignments?userId=${userId}", methodType = HttpMethodType.GET)
public class GetUserAssignmentsByIdMethod extends AbstractApiMethodV2 {
    public GetUserAssignmentsByIdMethod(int userId) throws UnsupportedEncodingException {
        replaceUrlPlaceholder("projects_url", APIContextManager.PROJECT_SERVICE_URL);
        replaceUrlPlaceholder("userId", String.valueOf(userId));

        String basicAuth = Base64Util
                .encodeString(CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.BASIC_AUTH_USERNAME_KEY))
                        + ":" + CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.BASIC_AUTTH_PASSWORD_KEY)));

        setHeaders("Authorization=Basic " + basicAuth);
    }
}
