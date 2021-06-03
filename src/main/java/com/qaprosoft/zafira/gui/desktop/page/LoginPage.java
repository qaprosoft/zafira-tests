package com.qaprosoft.zafira.gui.desktop.page;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {
    @FindBy(xpath = "//input[@name='username']")
    private ExtendedWebElement usernameField;

    @FindBy(xpath = "//input[@name='password']")
    private ExtendedWebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    private ExtendedWebElement submitButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSubmitButtonActive(){
        return submitButton.isClickable(5);
    }

    public TestRunsPage login(String username, String password){
        usernameField.type(username);
        passwordField.type(password);
        submitButton.click();
        return new TestRunsPage(getDriver());
    }
}
