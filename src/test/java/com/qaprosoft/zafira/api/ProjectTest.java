package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.project.DeleteProjectByIdMethod;
import com.qaprosoft.zafira.api.project.GetAllProjectMethod;
import com.qaprosoft.zafira.api.project.GetProjectByName;
import com.qaprosoft.zafira.api.project.PostProjectMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ProjectServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


public class ProjectTest extends ZafiraAPIBaseTest {
    private static Logger LOGGER = Logger.getLogger(ProjectTest.class);

    @Test
    public void testGetAllProjects() {
        GetAllProjectMethod getAllProjectMethod = new GetAllProjectMethod();
        apiExecutor.expectStatus(getAllProjectMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllProjectMethod);
        apiExecutor.validateResponse(getAllProjectMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCreateProject() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(10));
        PostProjectMethod postProjectMethod = new PostProjectMethod(projectName);
        apiExecutor.expectStatus(postProjectMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postProjectMethod);
        new ProjectServiceImpl().deleteProjectById(JsonPath.from(response).getInt(JSONConstant.ID_KEY));
        apiExecutor.validateResponse(postProjectMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testDeleteProjectById() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(10));
        ProjectServiceImpl projectService = new ProjectServiceImpl();
        String response = projectService.createProject(projectName);
        int projectId = JsonPath.from(response).getInt(JSONConstant.ID_KEY);

        DeleteProjectByIdMethod deleteProjectByIdMethod = new DeleteProjectByIdMethod(projectId);
        apiExecutor.expectStatus(deleteProjectByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteProjectByIdMethod);
        List<Integer> allProjectIds = projectService.getAllProjectsIds();
        LOGGER.info("All projects Ids:" + allProjectIds);
        Assert.assertFalse(allProjectIds.contains(projectId), "Project was not delete!");
    }

    @Test
    public void testGetProjectByName() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(10));
        ProjectServiceImpl projectService = new ProjectServiceImpl();
        String response = projectService.createProject(projectName);

        GetProjectByName getProjectByName = new GetProjectByName(projectName);
        apiExecutor.expectStatus(getProjectByName, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getProjectByName);
        projectService.deleteProjectById(JsonPath.from(response).getInt(JSONConstant.ID_KEY));
        apiExecutor.validateResponse(getProjectByName, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}

