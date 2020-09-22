package com.qaprosoft.zafira.gui.component.other;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.zafira.util.CommonUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class Calendar extends AbstractUIObject {

    @FindBy(xpath = ".//md-select[@placeholder = 'Month']")
    private ExtendedWebElement monthSelect;

    @FindBy(xpath = ".//md-select[@placeholder = 'Year']")
    private ExtendedWebElement yearSelect;

    @FindBy(css = ".md-date-range-picker__calendar .md-date-range-picker__calendar__grid span")
    private List<ExtendedWebElement> dates;

    public Calendar(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ExtendedWebElement getMonthSelect() {
        return monthSelect;
    }

    public void selectMonth(WebDriver driver, String value) {
        CommonUtils.select(monthSelect, driver, value);
    }

    public ExtendedWebElement getYearSelect() {
        return yearSelect;
    }

    public void selectYear(WebDriver driver, String value) {
        CommonUtils.select(yearSelect, driver, value);
    }

    public List<ExtendedWebElement> getDates() {
        return dates;
    }

}
