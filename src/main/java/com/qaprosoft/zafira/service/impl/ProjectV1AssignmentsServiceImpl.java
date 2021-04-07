package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.projectAssignments.DeleteProjectAssignmentMethod;
import com.qaprosoft.zafira.api.projectAssignments.GetProjectAssignmentsMethod;
import com.qaprosoft.zafira.api.projectAssignments.PutProjectAssignmentMethod;
import com.qaprosoft.zafira.api.projectsV1.DeleteProjectByKeyV1Method;
import com.qaprosoft.zafira.api.projectsV1.GetAllProjectsMethod;
import com.qaprosoft.zafira.api.projectsV1.GetProjectByIdOrKeyMethod;
import com.qaprosoft.zafira.api.projectsV1.PostProjectV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.ProjectV1AssignmentsService;
import com.qaprosoft.zafira.service.ProjectV1Service;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class ProjectV1AssignmentsServiceImpl implements ProjectV1AssignmentsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public List<Integer> getAllProjectAssignments(int projectId) {
        GetProjectAssignmentsMethod getProjectAssignmentsMethod = new GetProjectAssignmentsMethod(projectId);
        apiExecutor.expectStatus(getProjectAssignmentsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getProjectAssignmentsMethod);
        List<Integer> assignedUserList = JsonPath.from(rs).getList(JSONConstant.RESULTS_USER_ID);
        LOGGER.info("Assigned users: " + assignedUserList);
        return assignedUserList;
    }

    @Override
    public void assignUserToProject(int projectId, int userId, String role) {
        PutProjectAssignmentMethod putProjectAssignmentMethod = new PutProjectAssignmentMethod(projectId, userId, role);
        apiExecutor.expectStatus(putProjectAssignmentMethod, HTTPStatusCodeType.CREATED);
        apiExecutor.callApiMethod(putProjectAssignmentMethod);
    }

    @Override
    public void deleteUserAssignment(int projectId, int userId) {
        DeleteProjectAssignmentMethod deleteProjectAssignmentMethod = new DeleteProjectAssignmentMethod(projectId, userId);
        apiExecutor.expectStatus(deleteProjectAssignmentMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteProjectAssignmentMethod);
    }

}
