package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.gui.desktop.page.tenant.DashboardPage;
import com.qaprosoft.zafira.gui.desktop.page.tenant.MainDashboardsPage;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;

public class DashboardPagePageTest extends SignIn {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static String title;

    @AfterMethod(onlyForGroups = "add-edit-search")
    public void deleteExistingDashboard() {
        MainDashboardsPage mainDashboardsPage = navigationMenu.toMainDashboardPage();
        mainDashboardsPage.searchDashboard(title);
        mainDashboardsPage.deleteDashboard(title);
        mainDashboardsPage.searchDashboard("");
    }

    @Test(groups = "add-edit-search")
    public void addDashboard() {

        title = "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));

        MainDashboardsPage mainDashboardsPage = navigationMenu.toMainDashboardPage();
        DashboardPage dashboardPage = mainDashboardsPage.addDashboard(title);

        Assert.assertEquals(dashboardPage.getTitle(), title, "Title is not as expected!");
        navigationMenu.toMainDashboardPage();
        Assert.assertTrue(mainDashboardsPage.isDashboardPresentOnMainPage(title),
                "Can't find newly created dashboard");
    }

    @Test(groups = "add-edit-search")
    public void editDashboard() {
        title = "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));

        MainDashboardsPage mainDashboardsPage = navigationMenu.toMainDashboardPage();
        DashboardPage dashboardPage = mainDashboardsPage.addDashboard(title);

        title = "New_".concat(title);
        dashboardPage.editDashboard(title);

        Assert.assertEquals(dashboardPage.getTitle(), title, "Title is not as expected!");

        navigationMenu.toMainDashboardPage();

        Assert.assertTrue(mainDashboardsPage.isDashboardPresentOnMainPage(title),
                "Can't find newly created dashboard");
    }

    @Test(groups = "add-edit-search")
    public void searchDashboard() {
        title = "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));

        MainDashboardsPage mainDashboardsPage = navigationMenu.toMainDashboardPage();
        DashboardPage dashboardPage = mainDashboardsPage.addDashboard(title);
        navigationMenu.toMainDashboardPage();

        Assert.assertEquals(mainDashboardsPage.searchDashboard(title).get(0).getDashboardName(), title,
                "Can't find necessary dashboard!");
    }

    @Test
    public void deleteDashboard() {
        title = "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));

        MainDashboardsPage mainDashboardsPage = navigationMenu.toMainDashboardPage();
        mainDashboardsPage.addDashboard(title);
        navigationMenu.toMainDashboardPage();
        mainDashboardsPage.searchDashboard(title);

        mainDashboardsPage.deleteDashboard(title);
        mainDashboardsPage.searchDashboard("");
        navigationMenu.toMainDashboardPage();
        Assert.assertFalse(mainDashboardsPage.isDashboardPresentOnMainPage(title),
                "Dashboard with name " + title + " was not deleted!");
    }

    @Test
    public void getDashboardByName() {
        String expected = "рррр3";
        String expectedDate = "Jun 10, 2021";
        MainDashboardsPage mainDashboardsPage = navigationMenu.toMainDashboardPage();

        String actual = mainDashboardsPage.getDashboardByName(expected).getDashboardName();
        Assert.assertEquals(actual, expected, "Name is not as expected!");
        Assert.assertTrue(mainDashboardsPage.getDashboardByName(expected).isPresentCreatedDate());
        String actualDate = mainDashboardsPage.getDashboardByName(expected).getCreatedDate();
        Assert.assertEquals(actualDate, expectedDate, "Created date is not as expected!");
        mainDashboardsPage.getDashboardByName(expected).clickEdit();
        Assert.assertTrue(mainDashboardsPage.getDashboardByName(expected).isVisibleEdit());
    }
}
