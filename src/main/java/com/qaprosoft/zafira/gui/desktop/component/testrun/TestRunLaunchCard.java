package com.qaprosoft.zafira.gui.desktop.component.testrun;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TestRunLaunchCard extends TestRunCardBase {
    @FindBy(xpath = ".//div[@class='test-run-card__model ng-binding ng-scope']")
    private ExtendedWebElement capabilities;

    public TestRunLaunchCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
