package com.qaprosoft.zafira.service;

import java.util.List;

public interface ProjectV1AssignmentsService {


    List<Integer> getAllProjectAssignments(int projectId);

    void assignUserToProject(int projectId, int userId, String role);

    void deleteUserAssignment(int projectId, int userId);
}
