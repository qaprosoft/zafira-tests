package com.qaprosoft.zafira.api.failureTag;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.SuccessfulHttpStatus;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.carina.core.foundation.api.http.HttpResponseStatusType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/failureTag/_patch/rq.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
@Endpoint(url = "${base_api_url}/v1/failure-tags/${id}", methodType = HttpMethodType.PATCH)
public class PatchFailureTagMethod extends ZafiraBaseApiMethodWithAuth {

    public PatchFailureTagMethod(Boolean fallbackValue, int tagId) {
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(tagId));
        addProperty("value", String.valueOf(fallbackValue));
    }
}
