package com.qaprosoft.zafira.service;

import java.util.List;

public interface ProjectDashboardService {


    int createDashboard(int projectId, String dashboardName);

    void deleteDashboardById(int dashboardId);

    List<Integer> gelAllDashboardIds(int projectId);

    int createWidgetToDashboard(String body, int dashboardId);

}
