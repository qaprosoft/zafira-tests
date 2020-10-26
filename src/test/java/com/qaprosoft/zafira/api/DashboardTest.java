package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.dashboard.*;
import com.qaprosoft.zafira.api.dashboard.widget.DeleteWidgetFromDashboardMethod;
import com.qaprosoft.zafira.api.dashboard.widget.PostWidgetToDashboardMethod;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.domain.EmailMsg;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.DashboardServiceImpl;
import com.qaprosoft.zafira.service.impl.WidgetServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

public class DashboardTest extends ZafiraAPIBaseTest {
    private final static Logger LOGGER = Logger.getLogger(DashboardTest.class);
    private final static String TITLE_GENERAL = "User Performance";

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

    @Test
    public void testGetAllDashboard() {
        GetAllDashboardMethod getAllDashboardMethod = new GetAllDashboardMethod();
        apiExecutor.expectStatus(getAllDashboardMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllDashboardMethod);
        apiExecutor.validateResponse(getAllDashboardMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCreateDashboard() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        PostDashboardMethod postDashboardMethod = new PostDashboardMethod(dashboardName);
        apiExecutor.expectStatus(postDashboardMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postDashboardMethod);
        apiExecutor.validateResponse(postDashboardMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testDeleteDashboardById() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        DashboardServiceImpl dashboardService = new DashboardServiceImpl();
        int dashboardId = dashboardService.createDashboard(dashboardName);
        DeleteDashboardByIdMethod deleteDashboardByIdMethod = new DeleteDashboardByIdMethod(dashboardId);
        apiExecutor.expectStatus(deleteDashboardByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteDashboardByIdMethod);
        List<Integer> allDashboardsIds = dashboardService.gelAllDashboardsIds();
        Assert.assertFalse(allDashboardsIds.contains(dashboardId), "Dashbord was not delete!");
    }

    @Test
    public void testGetDashboardById() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        int dashboardId = new DashboardServiceImpl().createDashboard(dashboardName);
        GetDashboardByIdMethod getDashboardByIdMethod = new GetDashboardByIdMethod(dashboardId);
        apiExecutor.expectStatus(getDashboardByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getDashboardByIdMethod);
        apiExecutor.validateResponse(getDashboardByIdMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testUpdateDashboard() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        DashboardServiceImpl dashboardService = new DashboardServiceImpl();
        int dashboardId = dashboardService.createDashboard(dashboardName);
        String expectedDashboardName = R.TESTDATA.get(ConfigConstant.EXPECTED_DASHBOARD_NAME_KEY);
        PutDashboardMethod putDashboardMethod = new PutDashboardMethod(dashboardId, expectedDashboardName);
        apiExecutor.expectStatus(putDashboardMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(putDashboardMethod);
        apiExecutor.validateResponse(putDashboardMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String actualDashboardName = JsonPath.from(response).get(JSONConstant.TITLE_KEY);
        Assert.assertEquals(expectedDashboardName, actualDashboardName, "Dashboard name was not update!");
    }

    @Test
    public void testUpdateDashboardsOrder() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        DashboardServiceImpl dashboardService = new DashboardServiceImpl();
        int dashboardId = dashboardService.createDashboard(dashboardName);
        List<Integer> allDashboardsIds = dashboardService.gelAllDashboardsIds();
        LOGGER.info("Dashboards ids before update order: ".concat(String.valueOf(allDashboardsIds)));
        int positionNumber = (allDashboardsIds.size() - 3);
        PutDashboardOrderMethod putDashboardOrderMethod = new PutDashboardOrderMethod(dashboardId, positionNumber);
        apiExecutor.expectStatus(putDashboardOrderMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putDashboardOrderMethod);
        List<Integer> allDashboardsIdsAfterPut = dashboardService.gelAllDashboardsIds();
        LOGGER.info("Dashboards ids after update order: ".concat(String.valueOf(allDashboardsIdsAfterPut)));
        LOGGER.info("allDashboardsIds: ".concat(String.valueOf(allDashboardsIds)));
        LOGGER.info("positionNumber: ".concat(String.valueOf(positionNumber)));
        Assert.assertNotEquals(allDashboardsIds, allDashboardsIdsAfterPut, "Order was not update!");
    }

    @Test
    public void testCreateWidgetToDashboard() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        int dashboardId = new DashboardServiceImpl().createDashboard(dashboardName);
        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        String rs = widgetService.createWidgetToDashboard(widgetName).replace("\"location\":null", "\"location\":\"location\"");
        PostWidgetToDashboardMethod postWidgetToDashboardMethod = new PostWidgetToDashboardMethod(rs, dashboardId);
        apiExecutor.expectStatus(postWidgetToDashboardMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postWidgetToDashboardMethod);
        apiExecutor.validateResponse(postWidgetToDashboardMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int widgetId = JsonPath.from(rs).get(JSONConstant.ID_KEY);
        widgetService.deleteWidget(widgetId);
    }

    @Test
    public void testDeleteWidgetFromDashboardMethod() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        int dashboardId = new DashboardServiceImpl().createDashboard(dashboardName);
        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        String rs = widgetService.createWidgetToDashboard(widgetName).replace("\"location\":null", "\"location\":\"location\"");
        int widgetId = new DashboardServiceImpl().createWidgetToDashboard(rs, dashboardId);
        DeleteWidgetFromDashboardMethod deleteWidgetFromDashboardMethod = new DeleteWidgetFromDashboardMethod(dashboardId, widgetId);
        apiExecutor.expectStatus(deleteWidgetFromDashboardMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteWidgetFromDashboardMethod);
        widgetService.deleteWidget(widgetId);
    }

    @Test
    public void testGetDefaultDashboardIds() {
        GetDefaultDashboardIdsMethod getDefaultDashboardIdsMethod = new GetDefaultDashboardIdsMethod();
        apiExecutor.expectStatus(getDefaultDashboardIdsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getDefaultDashboardIdsMethod);
        apiExecutor.validateResponse(getDefaultDashboardIdsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetDashboardByTitleGENERAL() {
        GetDashboardByTitleMethod getDashboardByTitleMethod = new GetDashboardByTitleMethod(TITLE_GENERAL);
        apiExecutor.expectStatus(getDashboardByTitleMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(getDashboardByTitleMethod);
        apiExecutor.validateResponse(getDashboardByTitleMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(JsonPath.from(response).getString(JSONConstant.TITLE_KEY),TITLE_GENERAL,"Title is not as expected" );
    }
}


