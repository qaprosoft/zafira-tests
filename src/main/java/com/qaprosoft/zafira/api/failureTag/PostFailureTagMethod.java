package com.qaprosoft.zafira.api.failureTag;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.SuccessfulHttpStatus;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.carina.core.foundation.api.http.HttpResponseStatusType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/failureTag/_post/rq.json")
@ResponseTemplatePath(path = "api/failureTag/_post/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.CREATED_201)
@Endpoint(url = "${base_api_url}/v1/failure-tags", methodType = HttpMethodType.POST)
public class PostFailureTagMethod extends ZafiraBaseApiMethodWithAuth {

    public PostFailureTagMethod(String name) {
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setProperties("api/failure-tags.properties");
        addProperty("name", name);
    }
}
