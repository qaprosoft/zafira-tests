package com.qaprosoft.zafira.gui.desktop.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class RunResultDetailsBar extends AbstractUIObject {

    @FindBy(xpath = ".//div[@class='md-container md-ink-ripple']")
    private ExtendedWebElement checkbox;

    @FindBy(xpath = ".//md-input-container[contains(@class,'test-details__search')]")
    private ExtendedWebElement searchField;

    @FindBy(xpath = ".//span[text()='Group by']/ancestor::md-select-value[@class='md-select-value']")
    private ExtendedWebElement groupBy;

    @FindBy(xpath = ".//span[text()='Sort by']/ancestor::md-select-value[@class='md-select-value']")
    private ExtendedWebElement sortBy;

    @FindBy(xpath = ".//span[text()='Failed']/ancestor::button")
    private ExtendedWebElement failedButton;

    @FindBy(xpath = ".//span[text()='Skipped']/ancestor::button")
    private ExtendedWebElement skippedButton;

    @FindBy(xpath = ".//span[text()='Passed']/ancestor::button")
    private ExtendedWebElement passedButton;

    @FindBy(xpath = ".//span[text()='Aborted']/ancestor::button")
    private ExtendedWebElement abortedButton;

    @FindBy(xpath = ".//span[text()='In Progress']/ancestor::button")
    private ExtendedWebElement inProgressButton;

    @FindBy(id = "resetAllToggle")
    private ExtendedWebElement resetFiltersButton;

    public RunResultDetailsBar(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public boolean icCheckboxPresent() {
        return isElementPresent(checkbox);
    }

    public boolean isSearchFieldPresent(){
        return isElementPresent(searchField);
    }

    public boolean isGroupByPresent(){
        return isElementPresent(groupBy);
    }

    public boolean isSortByPresent(){
        return isElementPresent(sortBy);
    }

    public boolean isFailedButtonPresent(){
        return isElementPresent(failedButton);
    }

    public boolean isSkippedButtonPresent(){
        return isElementPresent(skippedButton);
    }

    public boolean isPassedButtonPresent(){
        return isElementPresent(passedButton);
    }

    public boolean isAbortedButtonPresent(){
        return isElementPresent(abortedButton);
    }

    public boolean isInProgressButtonPresent(){
        return isElementPresent(inProgressButton);
    }

    public boolean isResetButtonPresent(){
        return isElementPresent(resetFiltersButton);
    }

    private boolean isElementPresent(ExtendedWebElement element) {
        return element.isVisible(2) && element.isClickable(2);
    }
}
