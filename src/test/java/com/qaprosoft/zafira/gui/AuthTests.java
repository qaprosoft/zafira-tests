package com.qaprosoft.zafira.gui;

import com.qaprosoft.carina.core.foundation.utils.ownership.MethodOwner;
import com.qaprosoft.zafira.domain.Config;
import com.qaprosoft.zafira.service.impl.AuthServiceImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AuthTests extends BaseTest {

    private static final String INVALID_CREDENTIALS = "Invalid credentials";

    @BeforeMethod
    public void setup() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyValidLoginTest() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.waitProgressLinear();
        verifyLoginPage(loginPage);
        simpleLogin(loginPage, ADMIN_USERNAME, ADMIN_PASSWORD);
        DashboardPage dashboardPage = new DashboardPage(getDriver(), Config.DEFAULT_DASHBOARD_ID.getLongValue());
        Assert.assertTrue(dashboardPage.isPageOpened(), "Dashboard page not opened. Current URL: " + getDriver().getCurrentUrl());
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyInvalidLoginTest() {
        String invalidUsername = "invalid";
        String invalidPassword = "credentials";
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.waitProgressLinear();
        verifyLoginPage(loginPage);
        simpleLogin(loginPage, invalidUsername, invalidPassword);
        Assert.assertTrue(loginPage.getInvalidCredentialsLabel().isElementPresent(SMALL_TIMEOUT), "Invalid credentials alert not showed. Current Url: " + getDriver().getCurrentUrl());
        Assert.assertEquals(loginPage.getInvalidCredentialsLabelText(), INVALID_CREDENTIALS, "Invalid credentials text is not valid");
    }

    private void verifyLoginPage(LoginPage loginPage) {
        Assert.assertTrue(loginPage.getUsernameInput().isElementPresent(2), "Username input is not present");
        Assert.assertTrue(loginPage.getUsernameInput().getElement().isEnabled(), "Username input is not enabled");
        Assert.assertTrue(loginPage.getPasswordInput().isElementPresent(2), "Email input is not present");
        Assert.assertTrue(loginPage.getPasswordInput().getElement().isEnabled(), "Email input is not enabled");
        Assert.assertTrue(loginPage.getSigninButton().isElementPresent(2), "Signin button input is not present");
        Assert.assertFalse(loginPage.getSigninButton().getElement().isEnabled(), "Signin button input enabled");
    }

    private void simpleLogin(LoginPage loginPage, String username, String password) {
        AuthServiceImpl authService = new AuthServiceImpl(getDriver());
        authService.waitProgressLinear();
        loginPage.typeUsername(username);
        loginPage.typePassword(password);
        Assert.assertTrue(loginPage.getSigninButton().getElement().isEnabled(), "Login button is disabled after credentials setting");
        loginPage.clickSigninButton();
        authService.waitProgressLinear();
    }

}
