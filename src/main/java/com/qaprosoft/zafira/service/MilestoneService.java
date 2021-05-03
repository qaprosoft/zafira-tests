package com.qaprosoft.zafira.service;

import java.util.List;

public interface MilestoneService {

    int create(int projectId, String milestoneName);

    int create(int projectId);

    void delete(int projectId, int milestoneNameId);

    List<Integer> getAllMilestoneIdsInProject(int projectId);

    String getMilestoneNameById(int projectId, int milestoneId);

    String getMilestoneById(int projectId, int milestoneId);
}
