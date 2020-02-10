package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.dashboard.DeleteDashboardByIdMethod;
import com.qaprosoft.zafira.api.dashboard.GetAllDashboardMethod;
import com.qaprosoft.zafira.api.dashboard.GetDashboardByIdMethod;
import com.qaprosoft.zafira.api.dashboard.PostDashboardMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.DashboardServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

public class DashboardTest extends ZafiraAPIBaseTest {

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
}
