package com.qaprosoft.zafira.gui.desktop.component.dashboard;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//div[contains(@class,'md-select-menu-container md-active md-clickable')]
public class PeriodListMenu extends AbstractUIObject {
    @FindBy(xpath = ".//md-option[@value='Last 24 Hours']")
    private ExtendedWebElement last24Hours;

    @FindBy(xpath = ".//md-option[@value='Last 7 Days']")
    private ExtendedWebElement last7Days;

    @FindBy(xpath = ".//md-option[@value='Last 14 Days']")
    private ExtendedWebElement last14Days;

    @FindBy(xpath = ".//md-option[@value='Last 30 Days']")
    private ExtendedWebElement last30Days;

    @FindBy(xpath = ".//md-option[@value='Last 90 Days']")
    private ExtendedWebElement last90Days;

    @FindBy(xpath = ".//md-option[@value='Last 365 Days']")
    private ExtendedWebElement last365Days;

    @FindBy(xpath = ".//md-option[@value='Today']")
    private ExtendedWebElement today;

    @FindBy(xpath = ".//md-option[@value='Month']")
    private ExtendedWebElement month;

    @FindBy(xpath = ".//md-option[@value='Quarter']")
    private ExtendedWebElement quarter;

    @FindBy(xpath = ".//md-option[@value='Year']")
    private ExtendedWebElement year;

    @FindBy(xpath = ".//md-option[@value='Total']")
    private ExtendedWebElement total;

    public PeriodListMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
