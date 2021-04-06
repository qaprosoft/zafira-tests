package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.projectsV1.*;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ProjectV1ServiceImpl;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;


public class ProjectV1Test extends ZafiraAPIBaseTest {
    private static Logger LOGGER = Logger.getLogger(ProjectV1Test.class);
    private static ProjectV1ServiceImpl projectV1Service = new ProjectV1ServiceImpl();
    private static String projectKey;

    @AfterMethod
    public void testDeleteProject() {
        new ProjectV1ServiceImpl().deleteProjectByKey(projectKey);
    }

    @Test
    public void testGetAllProjects() {
        GetAllProjectsMethod getAllProjectsMethod = new GetAllProjectsMethod();
        apiExecutor.expectStatus(getAllProjectsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllProjectsMethod);
        apiExecutor.validateResponse(getAllProjectsMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey() + "results");
    }

    @Test
    public void testCreateProject() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(5));
        projectKey = RandomStringUtils.randomAlphabetic(3).toUpperCase(Locale.ROOT);
        PostProjectV1Method postProjectV1Method = new PostProjectV1Method(projectName, projectKey);
        apiExecutor.expectStatus(postProjectV1Method, HTTPStatusCodeType.CREATED);
        String rs = apiExecutor.callApiMethod(postProjectV1Method);
        apiExecutor.validateResponse(postProjectV1Method, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        projectKey = JsonPath.from(rs).getString(JSONConstant.KEY_KEY);
        List<String> projectKeys = projectV1Service.getAllProjectKeys();
        Assert.assertTrue(projectKeys.contains(projectKey), "Project was not created!");
    }

    @Test
    public void testDeleteProjectByKey() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(5));
        projectKey = RandomStringUtils.randomAlphabetic(3).toUpperCase(Locale.ROOT);
        projectV1Service.createProject(projectName, projectKey);

        DeleteProjectByKeyV1Method deleteProjectByKeyV1Method = new DeleteProjectByKeyV1Method(projectKey);
        apiExecutor.expectStatus(deleteProjectByKeyV1Method, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteProjectByKeyV1Method);
        List<String> projectKeys = projectV1Service.getAllProjectKeys();
        Assert.assertFalse(projectKeys.contains(projectKey), "Project was not deleted!");
    }

    @Test
    public void testGetProjectByKey() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(5));
        projectKey = RandomStringUtils.randomAlphabetic(3).toUpperCase(Locale.ROOT);
        projectV1Service.createProject(projectName, projectKey);

        GetProjectByIdOrKeyMethod getProjectByIdOrKeyMethod = new GetProjectByIdOrKeyMethod(projectKey);
        apiExecutor.expectStatus(getProjectByIdOrKeyMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getProjectByIdOrKeyMethod);
        apiExecutor.validateResponse(getProjectByIdOrKeyMethod, JSONCompareMode.STRICT);
        String actualProjectKey = JsonPath.from(rs).getString(JSONConstant.KEY_KEY);

        Assert.assertEquals(actualProjectKey, projectKey, "Project was not got!");
    }

    @Test
    public void testGetProjectById() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(5));
        projectKey = RandomStringUtils.randomAlphabetic(3).toUpperCase(Locale.ROOT);
        int projectId = projectV1Service.createProjectAndGetId(projectName, projectKey);

        GetProjectByIdOrKeyMethod getProjectByIdOrKeyMethod = new GetProjectByIdOrKeyMethod(projectId);
        apiExecutor.expectStatus(getProjectByIdOrKeyMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getProjectByIdOrKeyMethod);
        apiExecutor.validateResponse(getProjectByIdOrKeyMethod, JSONCompareMode.STRICT);
        int actualProjectId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);

        Assert.assertEquals(actualProjectId, projectId, "Project was not got!");
    }

    @Test(description = "without_lead")
    public void testUpdateProject() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(5));
        projectKey = RandomStringUtils.randomAlphabetic(3).toUpperCase(Locale.ROOT);
        projectV1Service.createProject(projectName, projectKey);
        String newName = "New_".concat(projectName);
        String newProjectKey = "NEW".concat(projectKey);

        PutProjectV1Method putProjectV1Method = new PutProjectV1Method(projectKey, newName, newProjectKey);
        apiExecutor.expectStatus(putProjectV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(putProjectV1Method);
        apiExecutor.validateResponse(putProjectV1Method, JSONCompareMode.STRICT);
        projectKey = JsonPath.from(rs).getString(JSONConstant.KEY_KEY);
        String actualProjectName = projectV1Service.getProjectNameByKey(newProjectKey);

        Assert.assertEquals(projectKey, newProjectKey, "Project key is not as expected!");
        Assert.assertEquals(actualProjectName, newName, "Project name is not as expected!");
    }
}

