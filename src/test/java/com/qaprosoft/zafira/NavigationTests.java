package com.qaprosoft.zafira;

import com.qaprosoft.carina.core.foundation.utils.ownership.MethodOwner;
import com.qaprosoft.zafira.domain.Config;
import com.qaprosoft.zafira.gui.BasePage;
import com.qaprosoft.zafira.gui.DashboardPage;
import com.qaprosoft.zafira.gui.component.SidebarMenu;
import com.qaprosoft.zafira.service.AuthService;
import com.qaprosoft.zafira.service.HeaderService;
import com.qaprosoft.zafira.service.SidebarService;
import com.qaprosoft.zafira.service.impl.AuthServiceImpl;
import com.qaprosoft.zafira.service.impl.HeaderServiceImpl;
import com.qaprosoft.zafira.service.impl.SidebarServiceImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NavigationTests extends BaseTest {

    private static final String DEFAULT_DASHBOARD_NAME = Config.DEFAULT_DASHBOARD_NAME.getValue();

    private DashboardPage dashboardPage;

    @BeforeMethod
    public void setup() {
        dashboardPage = signin();
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifySidebarElementsTest() {
        SidebarService sidebarService = new SidebarServiceImpl(getDriver(), dashboardPage);
        BasePage page = sidebarService.goToDashboardPage(DEFAULT_DASHBOARD_NAME);
        Assert.assertTrue(page.isPageOpened(), "Dashboard page is not opened");

        page = sidebarService.goToTestRunPage();
        Assert.assertTrue(page.isPageOpened(), "Test run page is not opened");

        SidebarMenu sidebarMenu = sidebarService.clickTestRunViewMenuButton();
        Assert.assertTrue(sidebarMenu.getCloseButton().isElementPresent(SMALL_TIMEOUT), "Test run views sidebar menu is not opened");

        page = sidebarService.goToUserPage();
        Assert.assertTrue(page.isPageOpened(), "User page is not opened");

        page = sidebarService.goToMonitorPage();
        Assert.assertTrue(page.isPageOpened(), "Monitor page is not opened");

        page = sidebarService.goToIntegrationPage();
        Assert.assertTrue(page.isPageOpened(), "Integration page is not opened");

        page = sidebarService.goToUserProfilePage();
        Assert.assertTrue(page.isPageOpened(), "User profile page is not opened");

        sidebarMenu = sidebarService.clickCompanyLogoMenuButton();
        Assert.assertTrue(sidebarMenu.getCloseButton().isElementPresent(SMALL_TIMEOUT), "Projects sidebar menu is not opened");

        HeaderService headerService = new HeaderServiceImpl(getDriver(), page);
        page = headerService.clickBrandImage();
        Assert.assertTrue(page.isPageOpened(), "Dashboard page is not opened after brand image clicking");

        AuthService authService = new AuthServiceImpl(getDriver());
        page = authService.logout();
        Assert.assertTrue(page.isPageOpened(), "Signin page is not opened after logout button clicking");
    }

}
