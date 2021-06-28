package com.qaprosoft.zafira.gui.desktop.component.user;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//md-dialog[@role='dialog']
public class UserProcessWindow extends AbstractPage {
    @FindBy(id = "modalTitle")
    private ExtendedWebElement modalTitle;

    @FindBy(xpath = "username")
    private ExtendedWebElement username;

    @FindBy(xpath = "firstName")
    private ExtendedWebElement firstName;

    @FindBy(id = "lastName")
    private ExtendedWebElement lastName;

    @FindBy(id = "email")
    private ExtendedWebElement email;

    @FindBy(id = "password")
    private ExtendedWebElement password;

    @FindBy(xpath = "//button[@type='submit']")
    private ExtendedWebElement submit;

    @FindBy(id = "deactivate")
    private ExtendedWebElement deactivate;

    @FindBy(id = "close")
    private ExtendedWebElement closeWindow;

    public UserProcessWindow(WebDriver driver) {
        super(driver);
    }
}
