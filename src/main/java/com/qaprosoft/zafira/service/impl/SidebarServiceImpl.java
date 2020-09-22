package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.exception.DashboardNotFoundException;
import com.qaprosoft.zafira.gui.BasePage;
import com.qaprosoft.zafira.gui.DashboardPage;
import com.qaprosoft.zafira.gui.GroupPage;
import com.qaprosoft.zafira.gui.IntegrationPage;
import com.qaprosoft.zafira.gui.InvitationPage;
import com.qaprosoft.zafira.gui.MonitorPage;
import com.qaprosoft.zafira.gui.TestRunPage;
import com.qaprosoft.zafira.gui.UserPage;
import com.qaprosoft.zafira.gui.UserProfilePage;
import com.qaprosoft.zafira.gui.component.Sidebar;
import com.qaprosoft.zafira.gui.component.sidebarmenu.DashboardSidebarMenu;
import com.qaprosoft.zafira.gui.component.sidebarmenu.ProjectSidebarMenu;
import com.qaprosoft.zafira.gui.component.sidebarmenu.TestRunViewSidebarMenu;
import com.qaprosoft.zafira.gui.component.sidebarmenu.UserSidebarMenu;
import com.qaprosoft.zafira.service.SidebarService;
import org.openqa.selenium.WebDriver;

public class SidebarServiceImpl extends BaseService implements SidebarService {

    private static final String ERR_MSG_DASHBOARD_NOT_FOUND = "Dashboard with name '%s' not found";

    public SidebarServiceImpl(WebDriver driver, BasePage basePage) {
        super(driver, basePage, null);
    }

    @Override
    public DashboardSidebarMenu clickDashboardMenuButton() {
        Sidebar sidebar = page.getSidebar();
        sidebar.clickDashboardMenuButton();
        return sidebar.getDashboardMenuButton();
    }

    @Override
    public DashboardPage goToDashboardPage(String name) {
        DashboardSidebarMenu dashboardSidebarMenu = clickDashboardMenuButton();
        waitProgressLinear();
        ExtendedWebElement dashboardButton = dashboardSidebarMenu.getItems().stream()
                                                                 .filter(item -> item.getText().equals(name))
                                                                 .findAny()
                                                                 .orElseThrow(() -> new DashboardNotFoundException(String.format(ERR_MSG_DASHBOARD_NOT_FOUND, name)));
        dashboardButton.click();
        String dashboardPath = dashboardButton.getAttribute("href");
        long dashboardId = Long.valueOf(dashboardPath.split("dashboards/")[1]);
        return new DashboardPage(driver, dashboardId);
    }

    @Override
    public TestRunPage goToTestRunPage() {
        Sidebar sidebar = page.getSidebar();
        sidebar.clickTestRunsButton();
        waitProgressLinear();
        return new TestRunPage(driver);
    }

    @Override
    public TestRunViewSidebarMenu clickTestRunViewMenuButton() {
        Sidebar sidebar = page.getSidebar();
        sidebar.clickTestRunsViewMenuButton();
        return sidebar.getTestRunsViewMenuButton();
    }

    @Override
    public UserPage goToUserPage() {
        Sidebar sidebar = page.getSidebar();
        sidebar.clickUsersButton();
        UserSidebarMenu userSidebarMenu = sidebar.getUsersButton();
        userSidebarMenu.clickUserButton();
        return new UserPage(driver);
    }

    @Override
    public GroupPage goToGroupPage() {
        Sidebar sidebar = page.getSidebar();
        sidebar.clickUsersButton();
        UserSidebarMenu userSidebarMenu = sidebar.getUsersButton();
        userSidebarMenu.clickGroupButton();
        return new GroupPage(driver);
    }

    @Override
    public InvitationPage goToInvitationPage() {
        Sidebar sidebar = page.getSidebar();
        sidebar.clickUsersButton();
        UserSidebarMenu userSidebarMenu = sidebar.getUsersButton();
        userSidebarMenu.clickInvitationButton();
        return new InvitationPage(driver);
    }

    @Override
    public MonitorPage goToMonitorPage() {
        Sidebar sidebar = page.getSidebar();
        sidebar.clickMonitorsButton();
        return new MonitorPage(driver);
    }

    @Override
    public IntegrationPage goToIntegrationPage() {
        Sidebar sidebar = page.getSidebar();
        sidebar.clickIntegrationsButton();
        return new IntegrationPage(driver);
    }

    @Override
    public UserProfilePage goToUserProfilePage() {
        Sidebar sidebar = page.getSidebar();
        sidebar.clickUserProfileButton();
        return new UserProfilePage(driver);
    }

    @Override
    public ProjectSidebarMenu clickCompanyLogoMenuButton() {
        Sidebar sidebar = page.getSidebar();
        sidebar.clickCompanyLogoButton();
        return sidebar.getCompanyLogoButton();
    }

}
