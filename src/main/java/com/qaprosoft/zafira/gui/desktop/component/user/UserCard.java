package com.qaprosoft.zafira.gui.desktop.component.user;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//div[@class='users-table__row ng-scope']
public class UserCard extends AbstractUIObject {
    @FindBy(xpath = "//md-dialog[@aria-label='Edit user profile']")
    private UserProcessWindow editWindow;

    @FindBy(xpath = "//md-dialog[@aria-label='Password']")
    private ChangePasswordWindow changePasswordWindow;

    @FindBy(xpath = ".//i[text()='person']/ancestor::span")
    private ExtendedWebElement id;

    @FindBy(xpath = ".//div[@data-title='Username']/span")
    private ExtendedWebElement username;

    @FindBy(xpath = ".//div[@name='userEmail']/span")
    private ExtendedWebElement email;

    @FindBy(xpath = ".//div[@name='userFirstLastName']/span")
    private ExtendedWebElement userFullName;

    @FindBy(xpath = ".//div[@data-title='Group']/span")
    private ExtendedWebElement group;

    @FindBy(xpath = ".//div[@data-title='Source']/span")
    private ExtendedWebElement source;

    @FindBy(xpath = ".//div[@data-title='Registration/ last activity']//div[@name='userCreated']")
    private ExtendedWebElement userCreated;

    @FindBy(xpath = ".//div[@data-title='Registration/ last activity']//span")
    private ExtendedWebElement lastActive;

    @FindBy(xpath = ".//div[@data-title='Status']/span")
    private ExtendedWebElement status;

    @FindBy(xpath = ".//button[@aria-label='User settings']")
    private ExtendedWebElement settings;

    @FindBy(xpath = "//div[@class='_md md-open-menu-container md-whiteframe-z2 md-active md-clickable']//button[@name='userEdit']")
    private ExtendedWebElement edit;

    @FindBy(xpath = "//div[@class='_md md-open-menu-container md-whiteframe-z2 md-active md-clickable']//button[@name='userChangePassword']")
    private ExtendedWebElement changePassword;

    public UserCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
