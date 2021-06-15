package com.qaprosoft.zafira.api.milestones;

import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@ResponseTemplatePath(path = "api/milestone/_put/rs.json")
@RequestTemplatePath(path = "api/milestone/_put/rq.json")
@Endpoint(url = "${api_url}/v1/milestones/${id}?projectId=${projectId}", methodType = HttpMethodType.PUT)
public class PutMilestoneMethod extends ZafiraBaseApiMethodWithAuth {
    public PutMilestoneMethod(int projectId, int milestoneId, String milestoneName) {
        replaceUrlPlaceholder("api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("id", String.valueOf(milestoneId));
        replaceUrlPlaceholder("projectId", String.valueOf(projectId));

        setProperties("api/milestone.properties");
        addProperty("name", milestoneName);
        addProperty("dueDate", OffsetDateTime.now().plusMonths(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
        addProperty("projectId", projectId);
        addProperty("completed", String.valueOf(false));
        addProperty("startDate", OffsetDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
    }
}
