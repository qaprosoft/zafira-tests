package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.projectsV1.DeleteProjectByKeyV1Method;
import com.qaprosoft.zafira.api.projectsV1.GetAllProjectsMethod;
import com.qaprosoft.zafira.api.projectsV1.GetProjectByIdOrKeyMethod;
import com.qaprosoft.zafira.api.projectsV1.PostProjectV1Method;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.constant.TestRailConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ProjectV1ServiceImpl;
import com.zebrunner.agent.core.annotation.TestLabel;
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
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40452")
    public void testGetAllProjects() {
        GetAllProjectsMethod getAllProjectsMethod = new GetAllProjectsMethod();

        apiExecutor.expectStatus(getAllProjectsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllProjectsMethod);
        apiExecutor.validateResponse(getAllProjectsMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey() + "results");
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40453")
    public void testCreateProject() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(5));
        projectKey = RandomStringUtils.randomAlphabetic(3).concat("1").toUpperCase(Locale.ROOT);

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
    public void testCreateProjectWithProjectKeyOnlyWithNumbers() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(5));
        projectKey = "1111";

        PostProjectV1Method postProjectV1Method = new PostProjectV1Method(projectName, projectKey);
        apiExecutor.expectStatus(postProjectV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postProjectV1Method);
    }

    @Test(description = "negative")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "407882")
    public void testCreateProjectWithEmptyRq() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(5));
        projectKey = RandomStringUtils.randomAlphabetic(3).concat("1").toUpperCase(Locale.ROOT);

        PostProjectV1Method postProjectV1Method = new PostProjectV1Method(projectName, projectKey);
        postProjectV1Method.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
        apiExecutor.expectStatus(postProjectV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postProjectV1Method);
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40454")
    public void testDeleteProjectByKey() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(5));
        projectKey = RandomStringUtils.randomAlphabetic(3).concat("1").toUpperCase(Locale.ROOT);
        projectV1Service.createProject(projectName, projectKey);

        DeleteProjectByKeyV1Method deleteProjectByKeyV1Method = new DeleteProjectByKeyV1Method(projectKey);
        apiExecutor.expectStatus(deleteProjectByKeyV1Method, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteProjectByKeyV1Method);

        List<String> projectKeys = projectV1Service.getAllProjectKeys();
        Assert.assertFalse(projectKeys.contains(projectKey), "Project was not deleted!");
    }

    @Test(description = "negative")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40789")
    public void testDeleteProjectByNonexistentKey() {
        String nonexistentProjectKey = RandomStringUtils.randomAlphabetic(5).toUpperCase(Locale.ROOT);

        DeleteProjectByKeyV1Method deleteProjectByKeyV1Method = new DeleteProjectByKeyV1Method(nonexistentProjectKey);
        apiExecutor.expectStatus(deleteProjectByKeyV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteProjectByKeyV1Method);
    }

    @Test(description = "negative")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40790")
    public void testGetProjectByNonexistentKey() {
        String nonexistentProjectKey = RandomStringUtils.randomAlphabetic(5).toUpperCase(Locale.ROOT);

        GetProjectByIdOrKeyMethod getProjectByIdOrKeyMethod = new GetProjectByIdOrKeyMethod(nonexistentProjectKey);
        apiExecutor.expectStatus(getProjectByIdOrKeyMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getProjectByIdOrKeyMethod);
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40455")
    public void testGetProjectByKey() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(5));
        projectKey = RandomStringUtils.randomAlphabetic(3).concat("1").toUpperCase(Locale.ROOT);
        projectV1Service.createProject(projectName, projectKey);

        GetProjectByIdOrKeyMethod getProjectByIdOrKeyMethod = new GetProjectByIdOrKeyMethod(projectKey);
        apiExecutor.expectStatus(getProjectByIdOrKeyMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getProjectByIdOrKeyMethod);
        apiExecutor.validateResponse(getProjectByIdOrKeyMethod, JSONCompareMode.STRICT);
        String actualProjectKey = JsonPath.from(rs).getString(JSONConstant.KEY_KEY);

        Assert.assertEquals(actualProjectKey, projectKey, "Project was not got!");
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40456")
    public void testGetProjectById() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(5));
        projectKey = RandomStringUtils.randomAlphabetic(3).concat("1").toUpperCase(Locale.ROOT);
        int projectId = projectV1Service.createProjectAndGetId(projectName, projectKey);

        GetProjectByIdOrKeyMethod getProjectByIdOrKeyMethod = new GetProjectByIdOrKeyMethod(projectId);
        apiExecutor.expectStatus(getProjectByIdOrKeyMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getProjectByIdOrKeyMethod);
        apiExecutor.validateResponse(getProjectByIdOrKeyMethod, JSONCompareMode.STRICT);
        int actualProjectId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);

        Assert.assertEquals(actualProjectId, projectId, "Project was not got!");
    }

    @Test(description = "negative")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40791")
    public void testGetProjectByNonexistentId() {
        String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(5));
        projectKey = RandomStringUtils.randomAlphabetic(3).concat("1").toUpperCase(Locale.ROOT);
        int projectId = projectV1Service.createProjectAndGetId(projectName, projectKey);
        projectV1Service.deleteProjectByKey(projectKey);

        GetProjectByIdOrKeyMethod getProjectByIdOrKeyMethod = new GetProjectByIdOrKeyMethod(projectId);
        apiExecutor.expectStatus(getProjectByIdOrKeyMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getProjectByIdOrKeyMethod);
    }
}

