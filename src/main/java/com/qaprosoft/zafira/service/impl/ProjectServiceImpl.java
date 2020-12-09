package com.qaprosoft.zafira.service.impl;

import io.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.project.DeleteProjectByIdMethod;
import com.qaprosoft.zafira.api.project.GetAllProjectMethod;
import com.qaprosoft.zafira.api.project.PostProjectMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.ProjectService;

import java.util.List;

public class ProjectServiceImpl implements ProjectService {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public List<Integer> getAllProjectsIds() {
        GetAllProjectMethod getAllProjectMethod = new GetAllProjectMethod();
        apiExecutor.expectStatus(getAllProjectMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(getAllProjectMethod);
        return JsonPath.from(response).getList(JSONConstant.ID_KEY);
    }

    @Override
    public void deleteProjectById(int projectId) {
        DeleteProjectByIdMethod deleteProjectByIdMethod = new DeleteProjectByIdMethod(projectId);
        apiExecutor.callApiMethod(deleteProjectByIdMethod);
    }

    @Override
    public int createProject(String projectName) {
        PostProjectMethod postProjectMethod = new PostProjectMethod(projectName);
        apiExecutor.expectStatus(postProjectMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postProjectMethod);
        return JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
    }
}
