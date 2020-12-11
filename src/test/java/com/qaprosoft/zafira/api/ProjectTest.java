package com.qaprosoft.zafira.api;

import io.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.project.*;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ProjectServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;


public class ProjectTest extends ZafiraAPIBaseTest {
    private static Logger LOGGER = Logger.getLogger(ProjectTest.class);
    private static int projectId;
    private static final  int NON_EXISTING_ID = 11111111;


    @AfterMethod
    public void testDeleteProject() {
        new ProjectServiceImpl().deleteProjectById(projectId);
    }

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
        apiExecutor.validateResponse(postProjectMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        projectId = JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Test
    public void testDeleteProjectById() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(10));
        ProjectServiceImpl projectService = new ProjectServiceImpl();
        projectId = projectService.createProject(projectName);
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
        projectId = projectService.createProject(projectName);
        GetProjectByName getProjectByName = new GetProjectByName(projectName);
        apiExecutor.expectStatus(getProjectByName, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getProjectByName);
        apiExecutor.validateResponse(getProjectByName, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testUpdateProject() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(10));
        ProjectServiceImpl projectService = new ProjectServiceImpl();
        projectId = projectService.createProject(projectName);
        String newName = "New" + projectName;
        PutProjectMethod putProjectMethod = new PutProjectMethod(newName, projectId);
        apiExecutor.expectStatus(putProjectMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(putProjectMethod);
        String expectedName = JsonPath.from(response).getString(JSONConstant.PROJECT_NAME_KEY);
        apiExecutor.validateResponse(putProjectMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(expectedName, newName, "Name is not updated!");
    }

    @Test(enabled = false)
    public void testUpdateProjectWithNonExistingId() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(10));
        ProjectServiceImpl projectService = new ProjectServiceImpl();
        projectId = projectService.createProject(projectName);
        PutProjectMethod putProjectMethod = new PutProjectMethod(projectName, NON_EXISTING_ID);
        apiExecutor.expectStatus(putProjectMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(putProjectMethod);
    }
}

