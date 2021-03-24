package com.qaprosoft.zafira.api.failureTagAssignment;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.SuccessfulHttpStatus;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.carina.core.foundation.api.http.HttpResponseStatusType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

@RequestTemplatePath(path = "api/failureTagAssignment/_post/rq.json")
@ResponseTemplatePath(path = "api/failureTagAssignment/_post/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
@Endpoint(url = "${base_api_url}/v1/failure-tag-assignments", methodType = HttpMethodType.POST)
public class PostFailureTagAssignmentMethod extends ZafiraBaseApiMethodWithAuth {

    public PostFailureTagAssignmentMethod(int testId,int tagId) {
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        setProperties("api/failure-tag-assignment.properties");
        addProperty("testId",testId);
        addProperty("tagId",tagId);
    }
}
