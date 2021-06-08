package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.gui.desktop.page.tenant.Dashboard;
import com.qaprosoft.zafira.gui.desktop.page.tenant.DashboardsPage;
import org.apache.commons.lang.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardPageTest extends SignIn {

    @Test
    public void addDashboard() {

        String title= "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));
        DashboardsPage dashboardsPage = navigationMenu.toDashboardPage();
        Dashboard dashboard = dashboardsPage.addDashboard(title);
        Assert.assertEquals(dashboard.getTitle(),title, "Title is not as expected!");
     //   Assert.assertTrue(dashboardsPage.getAllDashboard().contains(dashboard), "DashboardPage is not opened!");
    }
}
