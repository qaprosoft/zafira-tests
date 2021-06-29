package com.qaprosoft.zafira.gui.desktop.component.dashboard;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//div[@class='md-select-menu-container md-active md-clickable']
public class GroupByList extends AbstractUIObject {
    @FindBy(xpath = ".//md-option[@value='OWNER']")
    private ExtendedWebElement owner;

    @FindBy(xpath = ".//md-option[@value='ENV']")
    private ExtendedWebElement env;

    @FindBy(xpath = ".//md-option[@value='LOCALE']")
    private ExtendedWebElement locale;

    @FindBy(xpath = ".//md-option[@value='BUILD']")
    private ExtendedWebElement build;

    @FindBy(xpath = ".//md-option[@value='RUN_NAME']")
    private ExtendedWebElement runName;

    @FindBy(xpath = ".//md-option[@value='PLATFORM']")
    private ExtendedWebElement platform;

    @FindBy(xpath = ".//md-option[@value='BROWSER']")
    private ExtendedWebElement browser;

    @FindBy(xpath = ".//md-option[@value='PRIORITY']")
    private ExtendedWebElement priority;

    @FindBy(xpath = ".//md-option[@value='BUG']")
    private ExtendedWebElement bug;

    public GroupByList(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
