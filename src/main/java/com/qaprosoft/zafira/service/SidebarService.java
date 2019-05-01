package com.qaprosoft.zafira.service;

import com.qaprosoft.zafira.gui.DashboardPage;
import com.qaprosoft.zafira.gui.IntegrationPage;
import com.qaprosoft.zafira.gui.MonitorPage;
import com.qaprosoft.zafira.gui.TestRunPage;
import com.qaprosoft.zafira.gui.UserPage;
import com.qaprosoft.zafira.gui.UserProfilePage;
import com.qaprosoft.zafira.gui.component.sidebarmenu.DashboardSidebarMenu;
import com.qaprosoft.zafira.gui.component.sidebarmenu.ProjectSidebarMenu;
import com.qaprosoft.zafira.gui.component.sidebarmenu.TestRunViewSidebarMenu;

public interface SidebarService {

    DashboardSidebarMenu clickDashboardMenuButton();

    DashboardPage goToDashboardPage(String name);

    TestRunPage goToTestRunPage();

    TestRunViewSidebarMenu clickTestRunViewMenuButton();

    UserPage goToUserPage();

    MonitorPage goToMonitorPage();

    IntegrationPage goToIntegrationPage();

    UserProfilePage goToUserProfilePage();

    ProjectSidebarMenu clickCompanyLogoMenuButton();
}
