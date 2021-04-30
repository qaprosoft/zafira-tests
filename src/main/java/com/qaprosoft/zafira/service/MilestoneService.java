package com.qaprosoft.zafira.service;

import java.util.List;

public interface MilestoneService {

      int create(int projectId, String milestoneName);

      void delete(int projectId, int milestoneNameId);

      List<Integer> getAllMilestoneIdsInProject(int projectId);
}
