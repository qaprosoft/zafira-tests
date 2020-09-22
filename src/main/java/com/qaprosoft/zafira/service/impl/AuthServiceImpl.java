package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.domain.Config;
import com.qaprosoft.zafira.gui.DashboardPage;
import com.qaprosoft.zafira.gui.LoginPage;
import com.qaprosoft.zafira.gui.UserProfilePage;
import com.qaprosoft.zafira.service.AuthService;
import com.qaprosoft.zafira.service.SidebarService;
import com.qaprosoft.zafira.service.UserProfileService;
import com.qaprosoft.zafira.util.CommonUtils;
import org.openqa.selenium.WebDriver;

public class AuthServiceImpl extends BaseService<LoginPage> implements AuthService {

    private static final Long DEFAULT_DASHBOARD_ID = Config.DEFAULT_DASHBOARD_ID.getLongValue();

    public AuthServiceImpl(WebDriver driver) {
        super(driver, LoginPage.class);
    }

    public AuthServiceImpl(WebDriver driver, LoginPage basePage) {
        super(driver, basePage, LoginPage.class);
    }

    @Override
    public DashboardPage signin(String usernameOrEmail, String password) {
        LoginPage loginPage = getUIObject(driver);
        if (!loginPage.isPageOpened(0)) {
            loginPage.open();
        }
        loginPage.typeUsername(usernameOrEmail);
        loginPage.typePassword(password);
        CommonUtils.clickOutside(driver);
        loginPage.clickSigninButton();
        loginPage.waitUntilPageIsLoaded();
        return new DashboardPage(driver, DEFAULT_DASHBOARD_ID);
    }

    @Override
    public LoginPage logout() {
        LoginPage loginPage = getUIObject(driver);
        SidebarService sidebarService = new SidebarServiceImpl(driver, loginPage);
        UserProfilePage userProfilePage = sidebarService.goToUserProfilePage();
        UserProfileService userProfileService = new UserProfileServiceImpl(driver, userProfilePage);
        return userProfileService.clickLogoutButton();
    }

}
