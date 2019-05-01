package com.qaprosoft.zafira.gui;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.domain.Route;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "username")
    private ExtendedWebElement usernameInput;

    @FindBy(id = "password")
    private ExtendedWebElement passwordInput;

    @FindBy(xpath = "//*[contains(@class, 'danger')]//p")
    private ExtendedWebElement invalidCredentialsLabel;

    @FindBy(id = "signin")
    private ExtendedWebElement signinButton;

    @FindBy(linkText = "Reset")
    private ExtendedWebElement resetLink;

    public LoginPage(WebDriver driver) {
        super(driver, Route.SIGNIN);
    }

    public ExtendedWebElement getUsernameInput() {
        return usernameInput;
    }

    public void typeUsername(String username) {
        usernameInput.type(username);
    }

    public ExtendedWebElement getPasswordInput() {
        return passwordInput;
    }

    public void typePassword(String password) {
        passwordInput.type(password);
    }

    public ExtendedWebElement getInvalidCredentialsLabel() {
        return invalidCredentialsLabel;
    }

    public String getInvalidCredentialsLabelText() {
        return invalidCredentialsLabel.getText();
    }

    public ExtendedWebElement getSigninButton() {
        return signinButton;
    }

    public void clickSigninButton() {
        signinButton.click();
    }

    public ExtendedWebElement getResetLink() {
        return resetLink;
    }

    public void clickResetLink() {
        resetLink.click();
    }

}
