package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.projectAssignments.DeleteProjectAssignmentMethod;
import com.qaprosoft.zafira.api.projectAssignments.GetProjectAssignmentsMethod;
import com.qaprosoft.zafira.api.projectAssignments.PutProjectAssignmentMethod;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ProjectV1AssignmentsServiceImpl;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


public class ProjectV1AssignmentsTest extends ZafiraAPIBaseTest {
    private static Logger LOGGER = Logger.getLogger(ProjectV1AssignmentsTest.class);
    private static ProjectV1AssignmentsServiceImpl projectV1AssignmentsService = new ProjectV1AssignmentsServiceImpl();
    private static String projectKey;
    private static final String EXISTING_PROJECT_KEY = R.TESTDATA.get(ConfigConstant.EXISTING_PROJECT_KEY);
    private static final int EXISTING_PROJECT_ID = Integer.parseInt(R.TESTDATA.get(ConfigConstant.EXISTING_PROJECT_ID));


//    @AfterMethod
//    public void testDeleteProject() {
//        new ProjectV1ServiceImpl().deleteProjectByKey(projectKey);
//    }

    @Test
    //  @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40452")
    public void testGetAllProjects() {

        GetProjectAssignmentsMethod getProjectAssignmentsMethod = new GetProjectAssignmentsMethod(EXISTING_PROJECT_ID);
        apiExecutor.expectStatus(getProjectAssignmentsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getProjectAssignmentsMethod);
        apiExecutor.validateResponse(getProjectAssignmentsMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey() + "results");
    }

    @Test
    //  @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40452")
    public void testAssignUserToProject() {
        int userId = 8867;
        PutProjectAssignmentMethod putProjectAssignmentMethod = new PutProjectAssignmentMethod(EXISTING_PROJECT_ID, userId, "ADMINISTRATOR");
        apiExecutor.expectStatus(putProjectAssignmentMethod, HTTPStatusCodeType.CREATED);
        apiExecutor.callApiMethod(putProjectAssignmentMethod);
        apiExecutor.validateResponse(putProjectAssignmentMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey() + "items");
        List<Integer> assignedUserList = projectV1AssignmentsService.getAllProjectAssignments(EXISTING_PROJECT_ID);
        Assert.assertTrue(assignedUserList.contains(userId), "User with id " + userId + "wasn't assigned to the project!");
        projectV1AssignmentsService.deleteUserAssignment(EXISTING_PROJECT_ID, userId);
    }

    @Test(description = "negative_empty_rq", enabled = false)
    //  @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40452")
    public void testAssignUserToProjectWithEmptyRq() {
        PutProjectAssignmentMethod putProjectAssignmentMethod = new PutProjectAssignmentMethod(EXISTING_PROJECT_ID, 6, "GUEST");
        putProjectAssignmentMethod.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
        apiExecutor.expectStatus(putProjectAssignmentMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putProjectAssignmentMethod);
    }

    @Test
    //  @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40452")
    public void testDeleteUserAssignment() {
        int userId = 8867;
        projectV1AssignmentsService.assignUserToProject(EXISTING_PROJECT_ID, userId, "ADMINISTRATOR");
        DeleteProjectAssignmentMethod deleteProjectAssignmentMethod = new DeleteProjectAssignmentMethod(EXISTING_PROJECT_ID, userId);
        apiExecutor.expectStatus(deleteProjectAssignmentMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteProjectAssignmentMethod);
        List<Integer> assignedUserList = projectV1AssignmentsService.getAllProjectAssignments(EXISTING_PROJECT_ID);
        Assert.assertFalse(assignedUserList.contains(userId), "User with id " + userId + "wasn't assigned to the project!");
    }

    @Test(description = "negative_500", enabled = false)
    public void testDeleteUserAssignmentWithoutQueryParam() {
        int userId = 6;
        projectV1AssignmentsService.assignUserToProject(EXISTING_PROJECT_ID, userId, "ADMINISTRATOR");
        DeleteProjectAssignmentMethod deleteProjectAssignmentMethod = new DeleteProjectAssignmentMethod(EXISTING_PROJECT_ID, userId);
        deleteProjectAssignmentMethod.setMethodPath(deleteProjectAssignmentMethod.getMethodPath().split("\\?")[0]);
        apiExecutor.expectStatus(deleteProjectAssignmentMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteProjectAssignmentMethod);
    }
}

