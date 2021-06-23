package com.qaprosoft.zafira.gui.desktop.component.testresult;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//div[contains(@class,'test-details__header-actions _bulk')]
public class RunResultActionBar extends AbstractUIObject {
    @FindBy(xpath = ".//span[contains(@class,'test-details__test-checkbox-label')]")
    private ExtendedWebElement highlightedMethodsInfo;

    @FindBy(xpath = ".//span[contains(text(),'Mark as Passed')]/ancestor::button")
    private ExtendedWebElement markAsPassed;

    @FindBy(xpath = ".//span[contains(text(),'Mark as Failed')]/ancestor::button")
    private ExtendedWebElement markAsFailed;

    @FindBy(xpath = ".//span[contains(text(),'Link issue')]/ancestor::button")
    private ExtendedWebElement linkIssue;

    @FindBy(xpath = ".//md-icon[contains(@aria-label,'Reset selection')]/ancestor::button")
    private ExtendedWebElement closeBar;

    public RunResultActionBar(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
