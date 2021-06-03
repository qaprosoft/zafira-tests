package com.qaprosoft.zafira.api.failureTag;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.SuccessfulHttpStatus;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.carina.core.foundation.api.http.HttpResponseStatusType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/failureTag/_put/rq.json")
@ResponseTemplatePath(path = "api/failureTag/_put/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.CREATED_201)
@Endpoint(url = "${base_api_url}/v1/failure-tags/${id}", methodType = HttpMethodType.PUT)
public class PutFailureTagMethod extends ZafiraBaseApiMethodWithAuth {

    public PutFailureTagMethod(int tagId) {
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(tagId));
        setProperties("api/failure-tags.properties");
    }
}
