package com.qaprosoft.zafira;

import com.qaprosoft.carina.core.foundation.utils.ownership.MethodOwner;
import com.qaprosoft.zafira.gui.DashboardPage;
import com.qaprosoft.zafira.gui.UserPage;
import com.qaprosoft.zafira.service.SidebarService;
import com.qaprosoft.zafira.service.impl.SidebarServiceImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UserTests extends BaseTest {

    private UserPage userPage;

    @BeforeMethod
    public void setup() {
        DashboardPage dashboardPage = signin();
        SidebarService sidebarService = new SidebarServiceImpl(getDriver(), dashboardPage);
        this.userPage = sidebarService.goToUserPage();
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyNavigationTest() {
    }
}
