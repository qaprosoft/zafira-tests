package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.dashboard.*;
import com.qaprosoft.zafira.api.dashboard.attributes.DeleteDashboardAttributeMethod;
import com.qaprosoft.zafira.api.dashboard.attributes.PostABatchOfDashboardAttributesMethod;
import com.qaprosoft.zafira.api.dashboard.attributes.PostDashboardAttributeMethod;
import com.qaprosoft.zafira.api.dashboard.attributes.UpdateDashboardAttributeMethod;
import com.qaprosoft.zafira.api.dashboard.widget.DeleteWidgetFromDashboardMethod;
import com.qaprosoft.zafira.api.dashboard.widget.PostWidgetToDashboardMethod;
import com.qaprosoft.zafira.api.dashboard.widget.PutBatchOfWidgetsDashboardMethod;
import com.qaprosoft.zafira.api.dashboard.widget.PutWidgetDashboardMethod;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.DashboardServiceImpl;
import com.qaprosoft.zafira.service.impl.WidgetServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

public class DashboardWidgetTest extends ZafiraAPIBaseTest {
    private final static Logger LOGGER = Logger.getLogger(DashboardWidgetTest.class);
    private static int widgetId;


    @AfterTest
    public void DeleteCreatedDashboards() {
        DashboardServiceImpl dashboardService = new DashboardServiceImpl();
        List<Integer> allDashboardsIds = dashboardService.gelAllDashboardsIds();
        for (int i = 6; i <= Collections.max(allDashboardsIds); ++i) {
            if (allDashboardsIds.contains(i)) {
                dashboardService.deleteDashboardById(i);
            }
        }
    }

    @AfterMethod
    public void testDeleteWidget() {
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        widgetService.deleteWidget(widgetId);
    }

    @Test
    public void testCreateWidgetDashboard() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        int dashboardId = new DashboardServiceImpl().createDashboard(dashboardName);
        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        String rs = widgetService.createWidgetToDashboard(widgetName).replace("\"location\":null", "\"location\":\"location\"");
        PostWidgetToDashboardMethod postWidgetToDashboardMethod = new PostWidgetToDashboardMethod(rs, dashboardId);
        apiExecutor.expectStatus(postWidgetToDashboardMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postWidgetToDashboardMethod);
        apiExecutor.validateResponse(postWidgetToDashboardMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        widgetId = JsonPath.from(rs).get(JSONConstant.ID_KEY);
    }

    @Test
    public void testUpdateWidgetDashboard() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        int dashboardId = new DashboardServiceImpl().createDashboard(dashboardName);
        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        String rs = widgetService.createWidgetToDashboard(widgetName).replace("\"location\":null", "\"location\":\"location\"");
        widgetId = new DashboardServiceImpl().createWidgetToDashboard(rs, dashboardId);
        PutWidgetDashboardMethod putWidgetDashboardMethod = new PutWidgetDashboardMethod(dashboardId, widgetId);
        apiExecutor.expectStatus(putWidgetDashboardMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putWidgetDashboardMethod);
        apiExecutor.validateResponse(putWidgetDashboardMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testUpdateBatchOfWidgetDashboard() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        int dashboardId = new DashboardServiceImpl().createDashboard(dashboardName);
        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        String rs = widgetService.createWidgetToDashboard(widgetName).replace("\"location\":null", "\"location\":\"location\"");
        widgetId = new DashboardServiceImpl().createWidgetToDashboard(rs, dashboardId);
        PutBatchOfWidgetsDashboardMethod putBatchOfWidgetsDashboardMethod = new PutBatchOfWidgetsDashboardMethod(dashboardId, widgetId);
        apiExecutor.expectStatus(putBatchOfWidgetsDashboardMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putBatchOfWidgetsDashboardMethod);
        apiExecutor.validateResponse(putBatchOfWidgetsDashboardMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testDeleteWidgetDashboard() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        int dashboardId = new DashboardServiceImpl().createDashboard(dashboardName);
        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        String rs = widgetService.createWidgetToDashboard(widgetName).replace("\"location\":null", "\"location\":\"location\"");
        widgetId = new DashboardServiceImpl().createWidgetToDashboard(rs, dashboardId);
        DeleteWidgetFromDashboardMethod deleteWidgetFromDashboardMethod = new DeleteWidgetFromDashboardMethod(dashboardId, widgetId);
        apiExecutor.expectStatus(deleteWidgetFromDashboardMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteWidgetFromDashboardMethod);
    }


}


