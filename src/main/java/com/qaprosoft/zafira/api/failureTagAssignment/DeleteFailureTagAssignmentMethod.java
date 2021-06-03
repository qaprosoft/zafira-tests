package com.qaprosoft.zafira.api.failureTagAssignment;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;


@Endpoint(url = "${base_api_url}/v1/failure-tag-assignments/${tagId}", methodType = HttpMethodType.DELETE)
public class DeleteFailureTagAssignmentMethod extends ZafiraBaseApiMethodWithAuth {

    public DeleteFailureTagAssignmentMethod(int testId) {
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("tagId", String.valueOf(testId));
    }
}
