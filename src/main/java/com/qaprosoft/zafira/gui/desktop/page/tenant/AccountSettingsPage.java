package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.common.HelpMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.TenantHeader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class AccountSettingsPage extends AbstractPage {
    @FindBy(id = "header")
    private TenantHeader header;

    @FindBy(xpath = "//div[@data-embed='helpCenterForm']")
    private HelpMenu helpMenu;

    @FindBy(xpath = "//button[@aria-label='Help']")
    private ExtendedWebElement helpButton;

    @FindBy(id = "pageTitle")
    private ExtendedWebElement title;

    @FindBy(xpath = "//div[@class='profile-img']")
    private ExtendedWebElement photo;

    @FindBy(xpath = "//span[@class='md-caption no-margin ng-binding ng-scope' and contains(text(),'ui:')]")
    private ExtendedWebElement uiVersion;

    @FindBy(xpath = "//span[@class='md-caption no-margin ng-binding ng-scope' and contains(text(),'server')]")
    private ExtendedWebElement serverVersion;

    @FindBy(id = "serviceUrl")
    private ExtendedWebElement serviceUrl;

    @FindBy(xpath = "userAccessToken")
    private ExtendedWebElement userAccessToken;

    @FindBy(xpath = "//input[@name='hub-url']")
    private ExtendedWebElement hubUrl;

    @FindBy(id = "userUsername")
    private ExtendedWebElement userUsername;

    @FindBy(id = "userEmail")
    private ExtendedWebElement userEmail;

    @FindBy(id = "userFirstName")
    private ExtendedWebElement userFirstName;

    @FindBy(id = "userLastName")
    private ExtendedWebElement userLastName;

    @FindBy(id = "saveUserInfo")
    private ExtendedWebElement saveUserInfoButton;

    @FindBy(id = "userOldPassword")
    private ExtendedWebElement userOldPassword;

    @FindBy(id = "userPassword")
    private ExtendedWebElement userPassword;

    @FindBy(xpath = "//i[@class='fa fa-eye']")
    private ExtendedWebElement showPassIcon;

    @FindBy(id = "changePassword")
    private ExtendedWebElement changePasswordButton;


    public AccountSettingsPage(WebDriver driver) {
        super(driver);
    }
}
