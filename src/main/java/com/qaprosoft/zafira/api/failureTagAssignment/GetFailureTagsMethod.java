package com.qaprosoft.zafira.api.failureTagAssignment;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@ResponseTemplatePath(path = "api/failureTagAssignment/_get/rs.json")
@Endpoint(url = "${base_api_url}/v1/failure-tag-assignments?testId=${testId}", methodType = HttpMethodType.GET)
public class GetFailureTagsMethod extends ZafiraBaseApiMethodWithAuth {

    public GetFailureTagsMethod(int testId) {
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("testId", String.valueOf(testId));
    }
}
