package com.qaprosoft.zafira.gui;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.gui.desktop.component.NavigationMenu;
import com.qaprosoft.zafira.gui.desktop.page.tenant.Dashboard;
import com.qaprosoft.zafira.gui.desktop.page.tenant.DashboardsPage;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class DashboardPageTest extends SignIn {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static String title;

    @AfterMethod(onlyForGroups = "add-edit-search")
    public void deleteExistingDashboard() {
        DashboardsPage dashboardsPage = navigationMenu.toDashboardPage();
        dashboardsPage.searchDashboard(title);
        dashboardsPage.deleteDashboard(title);
    }

    @Test(groups = "add-edit-search")
    public void addDashboard() {

        title = "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));

        DashboardsPage dashboardsPage = navigationMenu.toDashboardPage();
        Dashboard dashboard = dashboardsPage.addDashboard(title);

        Assert.assertEquals(dashboard.getTitle(), title, "Title is not as expected!");
        NavigationMenu navigationMenuFromDashboard = dashboard.getNavigationMenu();
        dashboardsPage = navigationMenuFromDashboard.toDashboardPage();
        Assert.assertTrue(dashboardsPage.getAllDashboards().toString().contains(title),
                "Can't find newly created dashboard");
    }

    @Test(groups = "add-edit-search")
    public void editDashboard() {
        title = "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));

        DashboardsPage dashboardsPage = navigationMenu.toDashboardPage();
        Dashboard dashboard = dashboardsPage.addDashboard(title);

        title = "New_".concat(title);
        dashboard.editDashboard(title);

        Assert.assertEquals(dashboard.getTitle(), title, "Title is not as expected!");

        NavigationMenu navigationMenuFromDashboard = dashboard.getNavigationMenu();
        dashboardsPage = navigationMenuFromDashboard.toDashboardPage();

        Assert.assertTrue(dashboardsPage.getAllDashboards().toString().contains(title),
                "Can't find newly created dashboard");
    }

    @Test(groups = "add-edit-search")
    public void searchDashboard() {
        title = "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));

        DashboardsPage dashboardsPage = navigationMenu.toDashboardPage();
        Dashboard dashboard = dashboardsPage.addDashboard(title);
        navigationMenu.toDashboardPage();

        List<ExtendedWebElement> listOfDashboards = dashboardsPage.searchDashboard(title);

        LOGGER.info(listOfDashboards.toString());
        Assert.assertEquals(listOfDashboards.get(0).getText(), title,
                "Can't find necessary dashboard!");
    }

    @Test
    public void deleteDashboard() {
        title = "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));

        DashboardsPage dashboardsPage = navigationMenu.toDashboardPage();
        dashboardsPage.addDashboard(title);
        navigationMenu.toDashboardPage();
        dashboardsPage.searchDashboard(title);

        dashboardsPage.deleteDashboard(title);
        List<ExtendedWebElement> listOfDashboards = dashboardsPage.getAllDashboards();
        Assert.assertFalse(listOfDashboards.toString().contains(title),
                "Dashboard with name " + title + " was not deleted!");
    }

    @Test
    public void checkDefaultDashboardGeneral() {

        DashboardsPage dashboardsPage = navigationMenu.toDashboardPage();
        Dashboard dashboard = dashboardsPage.getGeneralDashboard();
        dashboard.getTitle();
        Assert.assertEquals( dashboard.getTitle(),"General", "Title is not as expected!");
        dashboard.sendByEmailButton().hover();
    //    LOGGER.info(dashboard.sendByEmailButton());
    }
}
