package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.ProjectMethods.DeleteProjectByIdMethod;
import com.qaprosoft.zafira.api.ProjectMethods.GetAllProjectMethod;
import com.qaprosoft.zafira.api.ProjectMethods.PostProjectMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.ProjectService;

public class ProjectServiceImpl implements ProjectService {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public String createProject(String projectName) {
        PostProjectMethod postProjectMethod = new PostProjectMethod(projectName);
        apiExecutor.expectStatus(postProjectMethod, HTTPStatusCodeType.OK);
        return apiExecutor.callApiMethod(postProjectMethod);
    }

    @Override
    public String getAllProjects() {
        GetAllProjectMethod getAllProjectMethod = new GetAllProjectMethod();
        apiExecutor.expectStatus(getAllProjectMethod, HTTPStatusCodeType.OK);
        return apiExecutor.callApiMethod(getAllProjectMethod);
    }

    @Override
    public void deleteProjectById(int projectId) {
        DeleteProjectByIdMethod deleteProjectByIdMethod = new DeleteProjectByIdMethod(projectId);
        apiExecutor.expectStatus(deleteProjectByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteProjectByIdMethod);
    }
}
