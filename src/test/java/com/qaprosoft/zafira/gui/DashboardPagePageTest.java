package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.gui.desktop.component.DashboardCard;
import com.qaprosoft.zafira.gui.desktop.page.tenant.DashboardPage;
import com.qaprosoft.zafira.gui.desktop.page.tenant.MainDashboardsPage;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DashboardPagePageTest extends SignIn {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static String title;

    @AfterMethod(onlyForGroups = "add-edit-search")
    public void deleteExistingDashboard() {
        MainDashboardsPage mainDashboardsPage = navigationMenu.toMainDashboardPage();
        DashboardCard dashboardCard = mainDashboardsPage.getDashboardByName(title);
        dashboardCard.deleteDashboard();
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

        DashboardCard dashboardCard = mainDashboardsPage.getDashboardByName(title);
        Assert.assertEquals(dashboardCard.getCreatedDate(), OffsetDateTime.now(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("MMM dd, yyyy")), "Create date is not as expected!");
        Assert.assertTrue(dashboardCard.isVisibleEdit(), "Button edit is not present on this dashboard!");
        Assert.assertTrue(dashboardCard.isVisibleDelete(), "Button delete is not present on this dashboard!");
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
        DashboardCard dashboardCard = mainDashboardsPage.getDashboardByName(title);

        dashboardCard.deleteDashboard();
        Assert.assertFalse(mainDashboardsPage.isDashboardPresentOnMainPage(title),
                "Dashboard with name " + title + " was not deleted!");
    }

    @Test
    public void checkGeneralDashboard() {
        String name = "General";
        String date = "Jun 4, 2021";
        MainDashboardsPage mainDashboardsPage = navigationMenu.toMainDashboardPage();
        DashboardCard dashboardCard = mainDashboardsPage.getDashboardByName(name);
        Assert.assertEquals(dashboardCard.getCreatedDate(), date, "Create date is not as expected!");
        Assert.assertFalse(dashboardCard.isVisibleEdit(), "Button edit is not present on this dashboard!");
        Assert.assertFalse(dashboardCard.isVisibleDelete(), "Button delete is not present on this dashboard!");
    }

    @Test
    public void checkPersonalDashboard() {
        String name = "Personal";
        String date = "Jun 4, 2021";
        MainDashboardsPage mainDashboardsPage = navigationMenu.toMainDashboardPage();
        DashboardCard dashboardCard = mainDashboardsPage.getDashboardByName(name);
        Assert.assertEquals(dashboardCard.getCreatedDate(), date, "Create date is not as expected!");
        Assert.assertFalse(dashboardCard.isVisibleEdit(), "Button edit is not present on this dashboard!");
        Assert.assertFalse(dashboardCard.isVisibleDelete(), "Button delete is not present on this dashboard!");
    }
}
