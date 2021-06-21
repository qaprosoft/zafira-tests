package com.qaprosoft.zafira.gui.impl;

import com.qaprosoft.zafira.constant.WebConstant;
import com.qaprosoft.zafira.gui.base.SignIn;
import com.qaprosoft.zafira.gui.desktop.component.dashboard.DashboardCard;
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
        mainDashboardsPage.deleteDashboard(title);
        Assert.assertFalse(mainDashboardsPage.isDashboardPresentOnMainPage(title),
                "Dashboard with name " + title + " was not deleted!");
    }

    @Test(groups = "add-edit-search")
    public void addDashboard() {
        title = "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));
        MainDashboardsPage mainDashboardsPage = navigationMenu.toMainDashboardPage();

        DashboardPage dashboardPage = mainDashboardsPage.addDashboard(title);
        dashboardPage.assertPageOpened();
        Assert.assertEquals(dashboardPage.getTitle(), title, "Title is not as expected!");

        mainDashboardsPage = navigationMenu.toMainDashboardPage();
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

        mainDashboardsPage = navigationMenu.toMainDashboardPage();
        Assert.assertTrue(mainDashboardsPage.isDashboardPresentOnMainPage(title),
                "Can't find newly created dashboard");
    }

    @Test(groups = "add-edit-search")
    public void searchDashboard() {
        title = "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));
        MainDashboardsPage mainDashboardsPage = navigationMenu.toMainDashboardPage();
        DashboardPage dashboardPage = mainDashboardsPage.addDashboard(title);
        dashboardPage.assertPageOpened();

        mainDashboardsPage = navigationMenu.toMainDashboardPage();

        Assert.assertEquals(mainDashboardsPage.searchDashboard(title).get(0).getDashboardName(), title,
                "Can't find necessary dashboard!");
        mainDashboardsPage.searchDashboard("");
    }

    @Test
    public void deleteDashboard() {
        title = "Dashboard_Name_".concat(RandomStringUtils.randomAlphabetic(6));
        MainDashboardsPage mainDashboardsPage = navigationMenu.toMainDashboardPage();
        DashboardPage dashboardPage = mainDashboardsPage.addDashboard(title);
        dashboardPage.assertPageOpened();

        mainDashboardsPage = navigationMenu.toMainDashboardPage();

        mainDashboardsPage.assertPageOpened();
        mainDashboardsPage.deleteDashboard(title);
        Assert.assertFalse(mainDashboardsPage.isDashboardPresentOnMainPage(title),
                "Dashboard with name " + title + " was not deleted!");
    }

    @Test
    public void checkGeneralDashboard() {
        MainDashboardsPage mainDashboardsPage = navigationMenu.toMainDashboardPage();
        DashboardCard dashboardCard = mainDashboardsPage.getDashboardByName(WebConstant.DASHBOARD_NAME_GENERAL);
        Assert.assertEquals(dashboardCard.getCreatedDate(), WebConstant.DASHBOARD_CREATION_DATE, "Create date is not as expected!");
        Assert.assertFalse(dashboardCard.isVisibleEdit(), "Button edit is not present on this dashboard!");
        Assert.assertFalse(dashboardCard.isVisibleDelete(), "Button delete is not present on this dashboard!");
    }

    @Test
    public void checkPersonalDashboard() {
        MainDashboardsPage mainDashboardsPage = navigationMenu.toMainDashboardPage();
        DashboardCard dashboardCard = mainDashboardsPage.getDashboardByName(WebConstant.DASHBOARD_NAME_PERSONAL);
        Assert.assertEquals(dashboardCard.getCreatedDate(), WebConstant.DASHBOARD_CREATION_DATE, "Create date is not as expected!");
        Assert.assertFalse(dashboardCard.isVisibleEdit(), "Button edit is not present on this dashboard!");
        Assert.assertFalse(dashboardCard.isVisibleDelete(), "Button delete is not present on this dashboard!");
    }

    @Test
    public void checkMainDashboardPage() {
        MainDashboardsPage mainDashboardsPage = navigationMenu.toMainDashboardPage();

        Assert.assertEquals(mainDashboardsPage.getTitle(), WebConstant.MAIN_DASHBOARD_PAGE_TITLE, "Title is not as expected!");
        Assert.assertTrue(mainDashboardsPage.isSearchPresentAndClickable(), "Search is not visible or clickable");
        Assert.assertTrue(mainDashboardsPage.isAddDashboardButtonPresentAndClickable(), "AddDashboard button is not visible or clickable");
        Assert.assertEquals(mainDashboardsPage.getColonNameDASBOARD_NAME(), WebConstant.COLUMN_NAME_DASHBOARD_NAME.toUpperCase(), "Colon name is not as expected!");
        Assert.assertEquals(mainDashboardsPage.getColonNameCREATION_DATE(), WebConstant.COLUMN_NAME_CREATION_DATE.toUpperCase(), "Colon name is not as expected!");
    }
}
