package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.gui.desktop.component.NavigationMenu;
import com.qaprosoft.zafira.gui.desktop.page.tenant.Dashboard;
import com.qaprosoft.zafira.gui.desktop.page.tenant.DashboardsPage;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;

public class DashboardPageTest extends SignIn {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Test
    public void addDashboard() throws InterruptedException {

        String title= "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));

        DashboardsPage dashboardsPage = navigationMenu.toDashboardPage();
        Dashboard dashboard = dashboardsPage.addDashboard(title);
        LOGGER.info(dashboard.getTitle());
        Assert.assertEquals(dashboard.getTitle(),title, "Title is not as expected!");
        NavigationMenu navigationMenuFromDashboard = dashboard.getNavigationMenu();
        dashboardsPage = navigationMenuFromDashboard.toDashboardPage();
        Assert.assertTrue(dashboardsPage.getAllDashboards().toString().contains(title),
                "Can't find newly created dashboard");
    }
}
