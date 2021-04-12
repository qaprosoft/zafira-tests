package com.qaprosoft.zafira.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qaprosoft.zafira.api.projectsV1.DeleteProjectByKeyV1Method;
import com.qaprosoft.zafira.api.projectsV1.GetAllProjectsMethod;
import com.qaprosoft.zafira.api.projectsV1.GetProjectByIdOrKeyMethod;
import com.qaprosoft.zafira.api.projectsV1.PostProjectV1Method;
import com.qaprosoft.zafira.bo.Project;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.ProjectV1Service;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Locale;

public class ProjectV1ServiceImpl implements ProjectV1Service {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();
    private static final ObjectMapper MAPPER = new ObjectMapper();

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
    public void deleteProjectById(int projectId) {
        String projectKey = getProjectKeyById(projectId);
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
    public int createProject() {
        String projectName = RandomStringUtils.randomAlphabetic(5).concat("_Project_name");
        String projectKey = projectName.substring(0,4).toUpperCase(Locale.ROOT);
        PostProjectV1Method postProjectV1Method = new PostProjectV1Method(projectName,projectKey);
        apiExecutor.expectStatus(postProjectV1Method, HTTPStatusCodeType.CREATED);
        String rs = apiExecutor.callApiMethod(postProjectV1Method);
        return JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
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

    @Override
    public String getProjectKeyById(int projectId) {
        GetProjectByIdOrKeyMethod getProjectByIdOrKeyMethod = new GetProjectByIdOrKeyMethod(projectId);
        apiExecutor.expectStatus(getProjectByIdOrKeyMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getProjectByIdOrKeyMethod);
        String projectKey = JsonPath.from(rs).getString(JSONConstant.KEY_KEY);
        return projectKey;
    }

    @Override
    public Project getProjectByKey(String projectKey) throws IOException {
        GetProjectByIdOrKeyMethod getProjectByIdOrKeyMethod = new GetProjectByIdOrKeyMethod(projectKey);
        apiExecutor.expectStatus(getProjectByIdOrKeyMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getProjectByIdOrKeyMethod);
        Project project = MAPPER.readValue(rs, Project.class);
        return project;
    }

    @Override
    public int getLeadIdByProjectKey(String projectKey) {
        GetProjectByIdOrKeyMethod getProjectByIdOrKeyMethod = new GetProjectByIdOrKeyMethod(projectKey);
        apiExecutor.expectStatus(getProjectByIdOrKeyMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getProjectByIdOrKeyMethod);
        int leadId = JsonPath.from(rs).getInt(JSONConstant.LEAD_ID);
        return leadId;
    }
}
