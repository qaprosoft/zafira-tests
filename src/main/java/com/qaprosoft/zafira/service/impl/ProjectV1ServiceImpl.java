package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.projectsV1.DeleteProjectByKeyV1Method;
import com.qaprosoft.zafira.api.projectsV1.GetAllProjectsMethod;
import com.qaprosoft.zafira.api.projectsV1.GetProjectByIdOrKeyMethod;
import com.qaprosoft.zafira.api.projectsV1.PostProjectV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.ProjectV1Service;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class ProjectV1ServiceImpl implements ProjectV1Service {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public List<String> getAllProjectKeys() {
        GetAllProjectsMethod getAllProjectsMethod = new GetAllProjectsMethod();
        apiExecutor.expectStatus(getAllProjectsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getAllProjectsMethod);
        List<String> projectList = JsonPath.from(rs).getList(JSONConstant.RESULTS_KEY_KEY);
        LOGGER.info("Existing projects: " + projectList);
        return projectList;
    }

    @Override
    public void deleteProjectByKey(String projectKey) {
        DeleteProjectByKeyV1Method deleteProjectByKeyV1Method = new DeleteProjectByKeyV1Method(projectKey);
        apiExecutor.callApiMethod(deleteProjectByKeyV1Method);
    }

    @Override
    public String createProject(String projectName, String projectKey) {
        PostProjectV1Method postProjectV1Method = new PostProjectV1Method(projectName,projectKey);
        apiExecutor.expectStatus(postProjectV1Method, HTTPStatusCodeType.CREATED);
        String rs = apiExecutor.callApiMethod(postProjectV1Method);
        return JsonPath.from(rs).getString(JSONConstant.KEY_KEY);
    }

    @Override
    public int createProjectAndGetId(String projectName, String projectKey) {
        PostProjectV1Method postProjectV1Method = new PostProjectV1Method(projectName,projectKey);
        apiExecutor.expectStatus(postProjectV1Method, HTTPStatusCodeType.CREATED);
        String rs = apiExecutor.callApiMethod(postProjectV1Method);
        return JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public String getProjectNameByKey(String projectKey) {
        GetProjectByIdOrKeyMethod getProjectByIdOrKeyMethod = new GetProjectByIdOrKeyMethod(projectKey);
        apiExecutor.expectStatus(getProjectByIdOrKeyMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getProjectByIdOrKeyMethod);
        String projectName = JsonPath.from(rs).getString(JSONConstant.NAME);
        return projectName;
    }
}
