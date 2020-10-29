package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.widget.DeleteWidgetMethod;
import com.qaprosoft.zafira.api.widget.GetAllWidgetMethod;
import com.qaprosoft.zafira.api.widget.GetAllWidgetTemplatesMethod;
import com.qaprosoft.zafira.api.widget.PostWidgetMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.WidgetService;

import java.util.List;

public class WidgetServiceImpl implements WidgetService {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public List<Integer> getAllWidgetIds() {
        GetAllWidgetMethod getAllWidgetMethod = new GetAllWidgetMethod();
        apiExecutor.expectStatus(getAllWidgetMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(getAllWidgetMethod);
        return JsonPath.from(response).getList(JSONConstant.ID_KEY);
    }

    @Override
    public void deleteWidget(int widgetId) {
        DeleteWidgetMethod deleteWidgetMethod = new DeleteWidgetMethod(widgetId);
        apiExecutor.expectStatus(deleteWidgetMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteWidgetMethod);
    }

    @Override
    public int createWidget(String widgetName) {
        PostWidgetMethod postWidgetMethod = new PostWidgetMethod(widgetName);
        apiExecutor.expectStatus(postWidgetMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postWidgetMethod);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public String createWidgetToDashboard(String widgetName) {
        PostWidgetMethod postWidgetMethod = new PostWidgetMethod(widgetName);
        apiExecutor.expectStatus(postWidgetMethod, HTTPStatusCodeType.OK);
       return apiExecutor.callApiMethod(postWidgetMethod);
    }

    @Override
    public int getWidgetTemplateId() {
        GetAllWidgetTemplatesMethod getAllWidgetTemplatesMethod = new GetAllWidgetTemplatesMethod();
        apiExecutor.expectStatus(getAllWidgetTemplatesMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getAllWidgetTemplatesMethod);
        return JsonPath.from(rs).getInt(JSONConstant.WIDGET_ID_KEY);
    }
}
