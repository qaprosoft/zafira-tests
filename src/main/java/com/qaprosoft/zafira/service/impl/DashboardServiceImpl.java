package com.qaprosoft.zafira.service.impl;

import io.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.dashboard.DeleteDashboardByIdMethod;
import com.qaprosoft.zafira.api.dashboard.GetAllDashboardMethod;
import com.qaprosoft.zafira.api.dashboard.PostDashboardMethod;
import com.qaprosoft.zafira.api.dashboard.attributes.PostDashboardAttributeMethod;
import com.qaprosoft.zafira.api.dashboard.widget.PostWidgetToDashboardMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.DashboardService;

import java.util.List;

public class DashboardServiceImpl implements DashboardService {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public int createDashboard(String dashboardName) {
        PostDashboardMethod postDashboardMethod = new PostDashboardMethod(dashboardName);
        apiExecutor.expectStatus(postDashboardMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postDashboardMethod);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void deleteDashboardById(int dashboardId) {
        DeleteDashboardByIdMethod deleteDashboardByIdMethod = new DeleteDashboardByIdMethod(dashboardId);
        apiExecutor.expectStatus(deleteDashboardByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteDashboardByIdMethod);
    }

    @Override
    public List<Integer> gelAllDashboardsIds() {
        GetAllDashboardMethod getAllDashboardMethod = new GetAllDashboardMethod();
        apiExecutor.expectStatus(getAllDashboardMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(getAllDashboardMethod);
        return JsonPath.from(response).getList(JSONConstant.ID_KEY);
    }

    @Override
    public int createWidgetToDashboard(String body, int dashboardId) {
        PostWidgetToDashboardMethod postWidgetToDashboardMethod = new PostWidgetToDashboardMethod(body, dashboardId);
        apiExecutor.expectStatus(postWidgetToDashboardMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postWidgetToDashboardMethod);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public int createDashboardAttribute(int dashboardId, String key, String value) {
        PostDashboardAttributeMethod postDashboardAttributeMethod = new PostDashboardAttributeMethod(dashboardId,key,value);
        apiExecutor.expectStatus(postDashboardAttributeMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postDashboardAttributeMethod);
        return JsonPath.from(response).getInt(JSONConstant.ATTRIBUTE_ID_KEY);
    }
}
