package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.project.GetUserAssignmentsByIdMethod;
import com.qaprosoft.zafira.constant.TestRailConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.enums.ProjectRole;
import com.qaprosoft.zafira.service.impl.ProjectV1AssignmentsServiceImpl;
import com.qaprosoft.zafira.service.impl.ProjectV1ServiceImpl;
import com.qaprosoft.zafira.service.impl.UserV1ServiceAPIImpl;
import com.zebrunner.agent.core.annotation.TestLabel;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.List;


public class UserAssignmentControllerTest extends ZafiraAPIBaseTest {
    private static Logger LOGGER = Logger.getLogger(UserAssignmentControllerTest.class);
    private static ProjectV1AssignmentsServiceImpl projectV1AssignmentsService = new ProjectV1AssignmentsServiceImpl();
    private static UserV1ServiceAPIImpl userServiceAPI = new UserV1ServiceAPIImpl();
    private static int projectId;
    private static int userId;

    @BeforeTest
    public void testCreateProject() {
        projectId = new ProjectV1ServiceImpl().createPrivateProject();
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
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40793")
    public void testGetAllUserAssignmentsWithUserInPrivateProject() throws UnsupportedEncodingException {
        userId = userServiceAPI.createForProject();
        projectV1AssignmentsService.assignUserToProject(projectId, userId, ProjectRole.GUEST.name());

        GetUserAssignmentsByIdMethod getProjectAssignmentsMethod = new GetUserAssignmentsByIdMethod(userId);
        apiExecutor.expectStatus(getProjectAssignmentsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getProjectAssignmentsMethod);
        apiExecutor.validateResponse(getProjectAssignmentsMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey() + "items");
        List<Integer> actualListIds = JsonPath.from(rs).getList("items.project.id");
        actualListIds.sort(Comparator.naturalOrder());

        List<Integer> allPublicProjectIds = new ProjectV1ServiceImpl().getAllPublicProjectIds();
        LOGGER.info("All public project Ids:  " + allPublicProjectIds);
        allPublicProjectIds.add(projectId);
        LOGGER.info("Actual projectIds in which the user is a member: " + actualListIds);

        Assert.assertEquals(actualListIds, allPublicProjectIds, "Actual projectIds aren't as expected!");
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40799")
    public void testGetAllUserAssignmentsWithNonexistentUserId() throws UnsupportedEncodingException {
        userId = userServiceAPI.createForProject();
        userServiceAPI.deleteUserById(userId);

        GetUserAssignmentsByIdMethod getProjectAssignmentsMethod = new GetUserAssignmentsByIdMethod(userId);
        apiExecutor.expectStatus(getProjectAssignmentsMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getProjectAssignmentsMethod);
    }


}

