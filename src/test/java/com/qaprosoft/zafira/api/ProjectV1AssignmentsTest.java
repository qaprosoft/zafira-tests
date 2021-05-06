package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.projectAssignments.DeleteProjectAssignmentMethod;
import com.qaprosoft.zafira.api.projectAssignments.GetProjectAssignmentsMethod;
import com.qaprosoft.zafira.api.projectAssignments.PutProjectAssignmentMethod;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.TestRailConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.enums.ProjectRole;
import com.qaprosoft.zafira.service.impl.ProjectV1AssignmentsServiceImpl;
import com.qaprosoft.zafira.service.impl.ProjectV1ServiceImpl;
import com.qaprosoft.zafira.service.impl.UserV1ServiceAPIImpl;
import com.zebrunner.agent.core.annotation.TestLabel;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;


public class ProjectV1AssignmentsTest extends ZafiraAPIBaseTest {
    private static Logger LOGGER = Logger.getLogger(ProjectV1AssignmentsTest.class);
    private static ProjectV1AssignmentsServiceImpl projectV1AssignmentsService = new ProjectV1AssignmentsServiceImpl();
    private static UserV1ServiceAPIImpl userServiceAPI = new UserV1ServiceAPIImpl();
    private static int projectId;
    private static final int EXISTING_USER_ID = userServiceAPI.getAllUserIds().get(1);
    private static int userId;

    @BeforeTest
    public void testCreateProject() {
        projectId = new ProjectV1ServiceImpl().createProject();
    }

    @AfterTest
    public void testDeleteProject() {
        new ProjectV1ServiceImpl().deleteProjectById(projectId);
    }

    @AfterMethod
    public void testDeleteUser() {
        userServiceAPI.deleteUserById(userId);
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40569")
    public void testGetAllProjectAssignments() {
        userId = userServiceAPI.createForProject();
        projectV1AssignmentsService.assignUserToProject(projectId, userId, ProjectRole.ADMINISTRATOR.name());
        projectV1AssignmentsService.assignUserToProject(projectId, userServiceAPI.getAllUserIds().get(0), ProjectRole.GUEST.name());

        GetProjectAssignmentsMethod getProjectAssignmentsMethod = new GetProjectAssignmentsMethod(projectId);
        apiExecutor.expectStatus(getProjectAssignmentsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getProjectAssignmentsMethod);
        apiExecutor.validateResponse(getProjectAssignmentsMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey() + "results");
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40571")
    public void testAssignUserToProject() {
        userId = userServiceAPI.createForProject();

        PutProjectAssignmentMethod putProjectAssignmentMethod =
                new PutProjectAssignmentMethod(projectId, userId, ProjectRole.GUEST.name());
        apiExecutor.expectStatus(putProjectAssignmentMethod, HTTPStatusCodeType.CREATED);
        apiExecutor.callApiMethod(putProjectAssignmentMethod);
        apiExecutor.validateResponse(putProjectAssignmentMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey() + "items");

        List<Integer> assignedUserList = projectV1AssignmentsService.getAllProjectAssignments(projectId);
        Assert.assertTrue(assignedUserList.contains(userId), "User with id " + userId + "wasn't assigned to the project!");
        projectV1AssignmentsService.deleteUserAssignment(projectId, userId);
    }

    @Test(description = "negative")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40573")
    public void testAssignUserToProjectWithInvalidRole() {
        userId = userServiceAPI.createForProject();

        PutProjectAssignmentMethod putProjectAssignmentMethod =
                new PutProjectAssignmentMethod(projectId, userId, "UNKNOWN");
        apiExecutor.expectStatus(putProjectAssignmentMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putProjectAssignmentMethod);
    }

    @Test(description = "negative_empty_rq")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40572")
    public void testAssignUserToProjectWithEmptyRq() {
        PutProjectAssignmentMethod putProjectAssignmentMethod =
                new PutProjectAssignmentMethod(projectId, EXISTING_USER_ID, ProjectRole.GUEST.name());
        putProjectAssignmentMethod.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
        apiExecutor.expectStatus(putProjectAssignmentMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putProjectAssignmentMethod);
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40574")
    public void testDeleteUserAssignment() {
        userId = userServiceAPI.createForProject();
        projectV1AssignmentsService.assignUserToProject(projectId, userId, ProjectRole.GUEST.name());
        projectV1AssignmentsService.assignUserToProject(projectId, userServiceAPI.getAllUserIds().get(0), ProjectRole.GUEST.name());

        DeleteProjectAssignmentMethod deleteProjectAssignmentMethod = new DeleteProjectAssignmentMethod(projectId, userId);
        apiExecutor.expectStatus(deleteProjectAssignmentMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteProjectAssignmentMethod);

        List<Integer> assignedUserList = projectV1AssignmentsService.getAllProjectAssignments(projectId);
        Assert.assertFalse(assignedUserList.contains(userId), "User with id " + userId + "wasn't assigned to the project!");
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40575")
    public void testDeleteUserAssignmentWithNonExistentAssignment() {
        userId = userServiceAPI.createForProject();
        projectV1AssignmentsService.assignUserToProject(projectId, userId, ProjectRole.GUEST.name());
        projectV1AssignmentsService.deleteUserAssignment(projectId, userId);

        DeleteProjectAssignmentMethod deleteProjectAssignmentMethod = new DeleteProjectAssignmentMethod(projectId, userId);
        apiExecutor.expectStatus(deleteProjectAssignmentMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteProjectAssignmentMethod);
    }

    @Test(description = "negative")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40576")
    public void testDeleteUserAssignmentWithoutQueryParam() {
        userId = userServiceAPI.createForProject();
        projectV1AssignmentsService.assignUserToProject(projectId, userId, ProjectRole.GUEST.name());

        DeleteProjectAssignmentMethod deleteProjectAssignmentMethod = new DeleteProjectAssignmentMethod(projectId, userId);
        deleteProjectAssignmentMethod.setMethodPath(deleteProjectAssignmentMethod.getMethodPath().split("\\?")[0]);
        apiExecutor.expectStatus(deleteProjectAssignmentMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(deleteProjectAssignmentMethod);
    }
}

