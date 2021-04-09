package com.qaprosoft.zafira.service;

import java.util.List;

public interface ProjectV1Service {

    List<String> getAllProjectKeys();

    void deleteProjectByKey(String projectKey);

    void deleteProjectById(int projectId);

    String createProject(String projectName, String projectKey);

    int createProject();

    int createProjectAndGetId(String projectName, String projectKey);

    String getProjectNameByKey(String projectKey);

    String getProjectKeyById(int projectId);

    int getLeadIdByProjectKey(String projectKey);
}
