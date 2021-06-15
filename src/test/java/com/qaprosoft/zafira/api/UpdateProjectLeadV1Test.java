package com.qaprosoft.zafira.api;

import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.projectsV1.PatchProjectV1Method;
import com.qaprosoft.zafira.api.projectsV1.PutProjectV1Method;
import com.qaprosoft.zafira.bo.Project;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.constant.TestRailConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.enums.ProjectRole;
import com.qaprosoft.zafira.service.impl.ProjectV1AssignmentsServiceImpl;
import com.qaprosoft.zafira.service.impl.ProjectV1ServiceImpl;
import com.qaprosoft.zafira.service.impl.UserV1ServiceAPIImpl;
import com.zebrunner.agent.core.annotation.TestLabel;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.Locale;


public class UpdateProjectLeadV1Test extends ZafiraAPIBaseTest {
    private static Logger LOGGER = Logger.getLogger(UpdateProjectLeadV1Test.class);
    private static ProjectV1ServiceImpl projectV1Service = new ProjectV1ServiceImpl();
    private static ProjectV1AssignmentsServiceImpl projectV1AssignmentsService = new ProjectV1AssignmentsServiceImpl();
    private static UserV1ServiceAPIImpl userV1ServiceAPI = new UserV1ServiceAPIImpl();
    private static String projectKey;
    private static int userId;
    private static int projectId;
    private static String projectName = "TestProject_".concat(RandomStringUtils.randomAlphabetic(5));

    @BeforeTest
    public void testCreateProject() {
        projectKey = RandomStringUtils.randomAlphabetic(3).toUpperCase(Locale.ROOT);
        projectId = projectV1Service.createProjectAndGetId(projectName, projectKey);
    }

    @AfterTest
    public void testDeleteProject() {
        new ProjectV1ServiceImpl().deleteProjectByKey(projectKey);
    }

    @AfterMethod
    public void testDeleteUser() {
        userV1ServiceAPI.deleteUserById(userId);
    }

    @DataProvider(name = "acceptable roles to become a lead")
    public static Object[][] getAcceptableRoles() {
        return new Object[][]{
                {"ADMINISTRATOR"},
                {"MANAGER"}};
    }

    @DataProvider(name = "unacceptable roles to become a lead")
    public static Object[][] getUnacceptableRoles() {
        return new Object[][]{
                {"GUEST"},
                {"ENGINEER"}};
    }

    @DataProvider(name = "mandatory fields for put")
    public static Object[][] getFields() {
        return new Object[][]{
                {"name"},
                {"key"}};
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40773")
    public void testUpdateProjectWithUserInAdministratorRole() throws IOException {

        userId = userV1ServiceAPI.createForProject();
        projectV1AssignmentsService.assignUserToProject(projectId, userId, ProjectRole.ADMINISTRATOR.name());

        String newName = "New_".concat(projectName);
        String newProjectKey = "NEW".concat(projectKey);

        PutProjectV1Method putProjectV1Method = new PutProjectV1Method(projectKey, newName, newProjectKey);
        putProjectV1Method.addProperty(JSONConstant.LEAD_ID_KEY, userId);
        putProjectV1Method.addProperty(JSONConstant.PUBLICLY_ACCESSIBLE, String.valueOf(false));
        apiExecutor.expectStatus(putProjectV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putProjectV1Method);
        apiExecutor.validateResponse(putProjectV1Method, JSONCompareMode.STRICT);

        Project actualProject = projectV1Service.getProjectByKey(newProjectKey);
        projectKey = actualProject.getKey();

        Assert.assertEquals(projectKey, newProjectKey, "Project key is not as expected!");
        Assert.assertEquals(actualProject.getLead().getId().intValue(), userId, "LeadId is not as expected!");
        Assert.assertEquals(actualProject.getId().intValue(), projectId, "Project id is not as expected!");
        Assert.assertEquals(actualProject.getName(), newName, "Project name is not as expected!");
        Assert.assertEquals(actualProject.isPubliclyAccessible(), false, "PubliclyAccessible is not as expected!");
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40773")
    public void testUpdateProjectWithUserInMangerRole() throws IOException {

        userId = userV1ServiceAPI.createForProject();
        projectV1AssignmentsService.assignUserToProject(projectId, userId, ProjectRole.MANAGER.name());

        PutProjectV1Method putProjectV1Method = new PutProjectV1Method(projectKey, projectName, projectKey);
        putProjectV1Method.addProperty(JSONConstant.LEAD_ID_KEY, userId);
        apiExecutor.expectStatus(putProjectV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putProjectV1Method);
        apiExecutor.validateResponse(putProjectV1Method, JSONCompareMode.STRICT);

        Project actualProject = projectV1Service.getProjectByKey(projectKey);
        projectKey = actualProject.getKey();

        Assert.assertEquals(actualProject.getLead().getId().intValue(), userId, "LeadId is not as expected!");
        Assert.assertEquals(actualProject.getId().intValue(), projectId, "Project id is not as expected!");
    }

    @Test(description = "negative", dataProvider = "unacceptable roles to become a lead")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40774")
    public void testUpdateProjectWithRole(String role) {

        userId = userV1ServiceAPI.createForProject();
        projectV1AssignmentsService.assignUserToProject(projectId, userId, role);

        PutProjectV1Method putProjectV1Method = new PutProjectV1Method(projectKey, projectName, projectKey);
        putProjectV1Method.addProperty(JSONConstant.LEAD_ID_KEY, userId);

        apiExecutor.expectStatus(putProjectV1Method, HTTPStatusCodeType.FORBIDDEN);
        apiExecutor.callApiMethod(putProjectV1Method);
    }

    @Test(description = "negative")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40775")
    public void testUpdateProjectLeadWithUnassignedUserInProject() {

        userId = userV1ServiceAPI.createForProject();

        PutProjectV1Method putProjectV1Method = new PutProjectV1Method(projectKey, projectName, projectKey);
        putProjectV1Method.addProperty(JSONConstant.LEAD_ID_KEY, userId);

        apiExecutor.expectStatus(putProjectV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(putProjectV1Method);
    }

    @Test(description = "negative")
    public void testUpdateProjectLeadWithEmptyRq() {
        userId = userV1ServiceAPI.createForProject();

        PutProjectV1Method putProjectV1Method = new PutProjectV1Method(projectKey, projectName, projectKey);
        putProjectV1Method.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
        apiExecutor.expectStatus(putProjectV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putProjectV1Method);
    }

    @Test(description = "negative", dataProvider = "mandatory fields for put")
    public void testUpdateProjectLeadWithoutMandatoryField(String field) {
        userId = userV1ServiceAPI.createForProject();

        PutProjectV1Method putProjectV1Method = new PutProjectV1Method(projectKey, projectName, projectKey);
        putProjectV1Method.removeProperty(field);

        apiExecutor.expectStatus(putProjectV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putProjectV1Method);
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40776")
    public void testPatchLeadIdProjectWithUnassignedUserInProject() {
        userId = userV1ServiceAPI.createForProject();

        PatchProjectV1Method patchProjectV1Method = new PatchProjectV1Method(projectKey, userId);
        apiExecutor.expectStatus(patchProjectV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(patchProjectV1Method);
    }

    @Test(dataProvider = "acceptable roles to become a lead")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40778")
    public void testPatchLeadIdProjectWithUserInAcceptableRole(String role) {

        userId = userV1ServiceAPI.createForProject();
        projectV1AssignmentsService.assignUserToProject(projectId, userId, role);

        PatchProjectV1Method patchProjectV1Method = new PatchProjectV1Method(projectKey, userId);

        apiExecutor.expectStatus(patchProjectV1Method, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(patchProjectV1Method);
        int actualLeadId = projectV1Service.getLeadIdByProjectKey(projectKey);

        Assert.assertEquals(actualLeadId, userId, "Project key is not as expected!");
    }

    @Test(dataProvider = "unacceptable roles to become a lead")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40777")
    public void testPatchLeadIdProjectWithUnacceptableRole(String role) {

        userId = userV1ServiceAPI.createForProject();
        projectV1AssignmentsService.assignUserToProject(projectId, userId, role);

        PatchProjectV1Method patchProjectV1Method = new PatchProjectV1Method(projectKey, userId);

        apiExecutor.expectStatus(patchProjectV1Method, HTTPStatusCodeType.FORBIDDEN);
        apiExecutor.callApiMethod(patchProjectV1Method);
    }
}

