package com.qaprosoft.zafira.api.failureTagAssignment;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.SuccessfulHttpStatus;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.carina.core.foundation.api.http.HttpResponseStatusType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

@RequestTemplatePath(path = "api/failureTagAssignment/_patch/rq.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
@Endpoint(url = "${base_api_url}/v1/failure-tag-assignments/${id}", methodType = HttpMethodType.PATCH)
public class PatchFailureTagAssignmentMethod extends ZafiraBaseApiMethodWithAuth {

    public PatchFailureTagAssignmentMethod(String feedbackValue,int tagId) {
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(tagId));
        addProperty("value",feedbackValue);
    }
}
