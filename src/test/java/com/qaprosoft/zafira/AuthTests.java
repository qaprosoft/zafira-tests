package com.qaprosoft.zafira;

import com.qaprosoft.carina.core.foundation.utils.ownership.MethodOwner;
import com.qaprosoft.zafira.domain.Config;
import com.qaprosoft.zafira.gui.DashboardPage;
import com.qaprosoft.zafira.gui.LoginPage;
import com.qaprosoft.zafira.service.AuthService;
import com.qaprosoft.zafira.service.impl.AuthServiceImpl;
import com.qaprosoft.zafira.util.CommonUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AuthTests extends BaseTest {

    private static final String INVALID_CREDENTIALS = "Invalid credentials";

    private LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        this.loginPage = new LoginPage(getDriver());
        this.loginPage.open();
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyValidLoginTest() {
        AuthService authService = new AuthServiceImpl(getDriver());
        authService.waitProgressLinear();
        authService.signin(ADMIN_USERNAME, ADMIN_PASSWORD);
        authService.waitProgressLinear();
        DashboardPage dashboardPage = new DashboardPage(getDriver(), Config.DEFAULT_DASHBOARD_ID.getLongValue());
        Assert.assertTrue(dashboardPage.isPageOpened(), "Dashboard page not opened");
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyInvalidLoginTest() {
        String invalidUsername = "invalid";
        String invalidPassword = "credentials";
        AuthService authService = new AuthServiceImpl(getDriver());
        authService.waitProgressLinear();
        simpleLogin(invalidUsername, invalidPassword);
        authService.waitProgressLinear();
        Assert.assertTrue(loginPage.getInvalidCredentialsLabel().isElementPresent(SMALL_TIMEOUT), "Invalid credentials alert not showed");
        Assert.assertEquals(loginPage.getInvalidCredentialsLabelText(), INVALID_CREDENTIALS, "Invalid credentials text is not valid");
    }

    private void simpleLogin(String username, String password) {
        loginPage.typeUsername(username);
        loginPage.typePassword(password);
        CommonUtils.clickOutside(getDriver());
        loginPage.clickSigninButton();
    }

}
