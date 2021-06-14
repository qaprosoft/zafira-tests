package com.qaprosoft.zafira.gui;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.gui.desktop.component.NavigationMenu;
import com.qaprosoft.zafira.gui.desktop.page.tenant.DashboardPage;
import com.qaprosoft.zafira.gui.desktop.page.tenant.MainDashboardsPage;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class DashboardPagePageTest extends SignIn {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static String title;

    @AfterMethod(onlyForGroups = "add-edit-search")
    public void deleteExistingDashboard() {
        MainDashboardsPage mainDashboardsPage = navigationMenu.toDashboardPage();
        mainDashboardsPage.searchDashboard(title);
        mainDashboardsPage.deleteDashboard(title);
    }

    @Test(groups = "add-edit-search")
    public void addDashboard() {

        title = "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));

        MainDashboardsPage mainDashboardsPage = navigationMenu.toDashboardPage();
        DashboardPage dashboardPage = mainDashboardsPage.addDashboard(title);

        Assert.assertEquals(dashboardPage.getTitle(), title, "Title is not as expected!");
        NavigationMenu navigationMenuFromDashboard = dashboardPage.getNavigationMenu();
        mainDashboardsPage = navigationMenuFromDashboard.toDashboardPage();
        Assert.assertTrue(mainDashboardsPage.getAllDashboards().toString().contains(title),
                "Can't find newly created dashboard");
    }

    @Test(groups = "add-edit-search")
    public void editDashboard() {
        title = "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));

        MainDashboardsPage mainDashboardsPage = navigationMenu.toDashboardPage();
        DashboardPage dashboardPage = mainDashboardsPage.addDashboard(title);

        title = "New_".concat(title);
        dashboardPage.editDashboard(title);

        Assert.assertEquals(dashboardPage.getTitle(), title, "Title is not as expected!");

        NavigationMenu navigationMenuFromDashboard = dashboardPage.getNavigationMenu();
        mainDashboardsPage = navigationMenuFromDashboard.toDashboardPage();

        Assert.assertTrue(mainDashboardsPage.getAllDashboards().toString().contains(title),
                "Can't find newly created dashboard");
    }

    @Test(groups = "add-edit-search")
    public void searchDashboard() {
        title = "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));

        MainDashboardsPage mainDashboardsPage = navigationMenu.toDashboardPage();
        DashboardPage dashboardPage = mainDashboardsPage.addDashboard(title);
        navigationMenu.toDashboardPage();

        List<ExtendedWebElement> listOfDashboards = mainDashboardsPage.searchDashboard(title);

        LOGGER.info(listOfDashboards.toString());
        Assert.assertEquals(listOfDashboards.get(0).getText(), title,
                "Can't find necessary dashboard!");
    }

    @Test
    public void deleteDashboard() {
        title = "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));

        MainDashboardsPage mainDashboardsPage = navigationMenu.toDashboardPage();
        mainDashboardsPage.addDashboard(title);
        navigationMenu.toDashboardPage();
        mainDashboardsPage.searchDashboard(title);

        mainDashboardsPage.deleteDashboard(title);
        List<ExtendedWebElement> listOfDashboards = mainDashboardsPage.getAllDashboards();
        Assert.assertFalse(listOfDashboards.toString().contains(title),
                "Dashboard with name " + title + " was not deleted!");
    }

    @Test
    public void checkDefaultDashboardGeneral() {

        MainDashboardsPage mainDashboardsPage = navigationMenu.toDashboardPage();
        DashboardPage dashboardPage = mainDashboardsPage.getGeneralDashboard();
        dashboardPage.getTitle();
        Assert.assertEquals(dashboardPage.getTitle(), "General", "Title is not as expected!");
        dashboardPage.sendByEmailButton().hover();

        //    LOGGER.info(dashboard.sendByEmailButton());
    }

    @Test
    public void getDashboardByName() {
        String expected = "Personal";
        String expectedDate = "Jun 4, 2021";
        MainDashboardsPage mainDashboardsPage = navigationMenu.toDashboardPage();
        String actual = mainDashboardsPage.getDashboardByName(expected).getDashboardName();
        Assert.assertEquals(actual, expected, "Name is not as expected!");
        Assert.assertTrue(mainDashboardsPage.getDashboardByName(expected).isPresentCreatedDate());
        String actualDate = mainDashboardsPage.getDashboardByName(expected).getCreatedDate();
        Assert.assertEquals(actualDate, expectedDate, "Created date is not as expected!");
        mainDashboardsPage.getDashboardByName(expected).clickEdit();
        pause(5);
        //    LOGGER.info(dashboard.sendByEmailButton());
    }
}
