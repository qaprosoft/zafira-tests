package com.qaprosoft.zafira.service;

import java.util.List;

public interface ProjectService {

    String createProject(String projectName);

    List<Integer> getAllProjectsIds();

    void deleteProjectById(int projectId);
}
