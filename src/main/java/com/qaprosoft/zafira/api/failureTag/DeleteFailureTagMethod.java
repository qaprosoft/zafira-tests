package com.qaprosoft.zafira.api.failureTag;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;


@Endpoint(url = "${base_api_url}/v1/failure-tags/${tagId}", methodType = HttpMethodType.DELETE)
public class DeleteFailureTagMethod extends ZafiraBaseApiMethodWithAuth {

    public DeleteFailureTagMethod(int testId) {
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("tagId", String.valueOf(testId));
    }
}
