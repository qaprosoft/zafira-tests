package com.qaprosoft.zafira.service;

public interface ProjectService {

    String createProject(String projectName);

    String getAllProjects();

    void deleteProjectById(int projectId);
}
