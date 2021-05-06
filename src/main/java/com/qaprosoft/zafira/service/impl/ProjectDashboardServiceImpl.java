package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.dashboard.DeleteDashboardByIdMethod;
import com.qaprosoft.zafira.api.dashboard.GetAllDashboardMethod;
import com.qaprosoft.zafira.api.dashboard.widget.PostWidgetToDashboardMethod;
import com.qaprosoft.zafira.api.projectDashbords.GetSearchProjectDashboards;
import com.qaprosoft.zafira.api.projectDashbords.PostProjectDashboard;
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
        DeleteDashboardByIdMethod deleteDashboardByIdMethod = new DeleteDashboardByIdMethod(dashboardId);
        apiExecutor.expectStatus(deleteDashboardByIdMethod, HTTPStatusCodeType.OK);
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
    public int createWidgetToDashboard(String body, int dashboardId) {
        PostWidgetToDashboardMethod postWidgetToDashboardMethod = new PostWidgetToDashboardMethod(body, dashboardId);
        apiExecutor.expectStatus(postWidgetToDashboardMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postWidgetToDashboardMethod);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

}
