package com.qaprosoft.zafira.gui.desktop.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TenantHeader extends AbstractUIObject {
    @FindBy(className = "app-header__logo-icon")
    private ExtendedWebElement logoImage;

    @FindBy(xpath = ".//div[@class='app-header__project']//span[@class='md-select-icon']")
    private ExtendedWebElement showProjectButton;

    @FindBy(xpath = ".//md-menu[@class='md-menu ng-scope _md']//i[text()='help_outline']")
    private ExtendedWebElement helpButton;

    @FindBy(xpath = ".//md-menu[@class='md-menu ng-scope _md']//i[text()='account_balance_wallet']")
    private ExtendedWebElement accountButton;

    @FindBy(xpath = ".//md-list-item[@class='nav-item md-no-proxy ng-scope _md']//i[text()='build']")
    private ExtendedWebElement configurationButton;

    public TenantHeader(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
