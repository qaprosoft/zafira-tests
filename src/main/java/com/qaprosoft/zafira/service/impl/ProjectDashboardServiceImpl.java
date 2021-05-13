package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.projectDashbords.*;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.ProjectDashboardService;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

import java.util.List;

public class ProjectDashboardServiceImpl implements ProjectDashboardService {
    private static Logger LOGGER = Logger.getLogger(ProjectDashboardServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public int createDashboard(int projectId, String dashboardName) {
        PostProjectDashboard postProjectDashboard = new PostProjectDashboard(projectId, dashboardName);
        apiExecutor.expectStatus(postProjectDashboard, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postProjectDashboard);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void deleteDashboardById(int dashboardId) {
        DeleteProjectDashboardById deleteDashboardByIdMethod = new DeleteProjectDashboardById(dashboardId);
        apiExecutor.callApiMethod(deleteDashboardByIdMethod);
    }

    @Override
    public List<Integer> gelAllDashboardIds(int projectId) {
        GetSearchProjectDashboards searchProjectDashboards = new GetSearchProjectDashboards(projectId);
        apiExecutor.expectStatus(searchProjectDashboards, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(searchProjectDashboards);
        List<Integer> ids = JsonPath.from(response).getList(JSONConstant.RESULT_ID_KEY);
        LOGGER.info("Existing dashboard ids:  " + ids);
        return ids;
    }

    @Override
    public int createWidgetToDashboard(int widgetId, int dashboardId) {
        PostWidgetToProjectDashboard putProjectDashboard =
                new PostWidgetToProjectDashboard(dashboardId, widgetId);
        apiExecutor.expectStatus(putProjectDashboard, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(putProjectDashboard);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void deleteDashboardWidget(int dashboardId, int widgetId) {
        DeleteWidgetFromProjectDashboard deleteWidgetFromProjectDashboard =
                new DeleteWidgetFromProjectDashboard(dashboardId, widgetId);
        apiExecutor.callApiMethod(deleteWidgetFromProjectDashboard);
    }
}
