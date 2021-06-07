package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.gui.desktop.component.Dashboard;
import com.qaprosoft.zafira.gui.desktop.page.tenant.DashboardsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardPageTest extends SignIn {

    @Test
    public void addDashboard() {

        DashboardsPage dashboardsPage = navigationMenu.toDashboardPage();
        Dashboard dashboard = dashboardsPage.addDashboard();
        dashboard.createDashboard("new dashboard");
        Assert.assertTrue(dashboardsPage.isPageOpened(), "DashboardPage is not opened!");
    }
}
