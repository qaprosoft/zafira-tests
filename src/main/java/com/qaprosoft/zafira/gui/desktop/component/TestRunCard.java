package com.qaprosoft.zafira.gui.desktop.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TestRunCard extends TestRunCardBase {

    @FindBy(xpath = "//div[@class='test-run-card__cell _platform']")
    private ExtendedWebElement platform;

    @FindBy(xpath = "//span[contains(@class,'label-success')]")
    private ExtendedWebElement successTestsBox;

    public TestRunCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
