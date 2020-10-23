package com.qaprosoft.zafira.service;

import java.util.List;

public interface DashboardService {

    int createDashboard(String dashboardName);

    void deleteDashboardById(int dashboardId);

    List<Integer> gelAllDashboardsIds();

    int createWidgetToDashboard(String body, int dashboardId);
}
