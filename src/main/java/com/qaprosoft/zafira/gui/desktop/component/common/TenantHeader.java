package com.qaprosoft.zafira.gui.desktop.component.common;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//id = "header"
public class TenantHeader extends AbstractUIObject {
    @FindBy(xpath = "//div[contains(@class,'md-select-menu-container project-settings__select md-active md-clickable')]")
    private ProjectsMenu projectsMenu;

    @FindBy(className = "app-header__logo-icon")
    private ExtendedWebElement logoImage;

    @FindBy(xpath = ".//div[@class='app-header__project']//span[@class='md-select-icon']")
    private ExtendedWebElement showProjectButton;

    //4 buttons on the left of a header
    @FindBy(xpath = ".//md-menu[@class='md-menu ng-scope _md']//i[text()='help_outline']")
    private ExtendedWebElement helpButton;

    @FindBy(xpath = ".//md-menu[@class='md-menu ng-scope _md']//i[text()='account_balance_wallet']")
    private ExtendedWebElement walletButton;

    @FindBy(xpath = ".//md-list-item[contains(@class,'nav-item')]//i[text()='build']")
    private ExtendedWebElement configurationButton;

    @FindBy(xpath = ".//md-list-item[contains(@class,'nav-item')]//i[text()='person_outline']")
    private ExtendedWebElement membersButton;

    //header buttons sub buttons
    @FindBy(xpath = "//md-menu-content[@class='nav-sublist']//span[contains(text(),'Documentation')]")
    private ExtendedWebElement helpDocumentation;

    @FindBy(xpath = "//md-menu-content[@class='nav-sublist']//span[contains(text(),'Get started')]")
    private ExtendedWebElement helpGetStarted;

    @FindBy(xpath = "//md-menu-content[@class='nav-sublist']//span[contains(text(),'Billing')]")
    private ExtendedWebElement walletBilling;

    @FindBy(xpath = "//md-menu-content[@class='nav-sublist']//span[contains(text(),'Subscription')]")
    private ExtendedWebElement walletSubscription;

    @FindBy(xpath = "//md-menu-content[@class='nav-sublist']//span[contains(text(),'Account settings')]")
    private ExtendedWebElement memberAccountSettings;

    @FindBy(xpath = "//md-menu-content[@class='nav-sublist']//span[contains(text(),'Logout')]")
    private ExtendedWebElement memberLogout;

    public TenantHeader(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void openProjectsWindow(){
        showProjectButton.click();
    }

    public ProjectsMenu getProjectsMenu() {
        showProjectButton.click();
        return projectsMenu;
    }
}
