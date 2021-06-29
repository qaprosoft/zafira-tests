package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.common.HelpMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.TenantHeader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ConfigurationPage extends AbstractPage {
    @FindBy(id = "header")
    private TenantHeader header;

    @FindBy(xpath = "//div[@data-embed='helpCenterForm']")
    private HelpMenu helpMenu;

    @FindBy(xpath = "//button[@aria-label='Help']")
    private ExtendedWebElement helpButton;

    @FindBy(id = "pageTitle")
    private ExtendedWebElement title;

    @FindBy(xpath = "//span[@class='md-caption no-margin ng-binding ng-scope' and contains(@ng-if,'ui')]")
    private ExtendedWebElement uiVersion;

    @FindBy(xpath = "//span[@class='md-caption no-margin ng-binding ng-scope' and contains(@ng-if,'service')]")
    private ExtendedWebElement serverVersion;

    @FindBy(xpath = "//span[@class='profile-photo__icon-wrapper _squared ng-scope' and contains(@ng-if,'companyLogoUrl')]//md-icon")
    private ExtendedWebElement companyLogo;

    @FindBy(xpath = "//p[contains(text(),'SSO configurations')]/ancestor::a")
    private ExtendedWebElement ssoConfiguration;

    @FindBy(xpath = "//p[contains(text(),'Users')]/ancestor::a")
    private ExtendedWebElement users;

    @FindBy(xpath = "//p[contains(text(),'Groups and permissions')]/ancestor::a")
    private ExtendedWebElement groupsAndPermissions;

    @FindBy(xpath = "//p[contains(text(),'Invitations')]/ancestor::a")
    private ExtendedWebElement invitations;

    @FindBy(xpath = "//p[contains(text(),'Integrations')]/ancestor::a")
    private ExtendedWebElement integrations;


    public ConfigurationPage(WebDriver driver) {
        super(driver);
    }
}
