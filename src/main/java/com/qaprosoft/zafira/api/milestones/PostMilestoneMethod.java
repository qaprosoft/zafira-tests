package com.qaprosoft.zafira.api.milestones;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@ResponseTemplatePath(path = "api/milestone/_post/rs.json")
@RequestTemplatePath(path = "api/milestone/_post/rq.json")
@Endpoint(url = "${api_url}/v1/milestones?projectId=${projectId}", methodType = HttpMethodType.POST)
public class PostMilestoneMethod extends ZafiraBaseApiMethodWithAuth {
    public PostMilestoneMethod(int projectId, String milestoneName) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));
        setProperties("api/milestone.properties");
        addProperty("name", milestoneName);
        addProperty("projectId", projectId);
        addProperty("dueDate", OffsetDateTime.now().plusMonths(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
        addProperty("startDate", OffsetDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
    }

    public void setAuthHeaders() {
        String accessToken = new APIContextManager().getAccessToken();
        setHeaders("Authorization=Bearer " + accessToken);
    }
}
