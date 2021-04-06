package com.qaprosoft.zafira.service;

import java.util.List;

public interface ProjectV1Service {

    List<String> getAllProjectKeys();

    void deleteProjectByKey(String projectKey);

    String createProject(String projectName, String projectKey);

    int createProjectAndGetId(String projectName, String projectKey);

    String getProjectNameByKey(String projectKey);
}
