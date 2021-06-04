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

    @FindBy (xpath = "//span[contains(@title,'Failed')]")
    private ExtendedWebElement failedTestsBox;

    @FindBy (xpath = "//span[contains(@title,'Skipped')]")
    private ExtendedWebElement skippedTestBox;

    @FindBy(xpath = "//div[@class='test-run-card__time light_text ng-scope']")
    private ExtendedWebElement testDuration;

    public TestRunCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public boolean isTestComplete(){
        return testDuration.isPresent();
    }

    public String getRunResult(){
        String failed = failedTestsBox.getText().replace('\n', ' ');
        return String.format("Passed %s, Failure %s, Skipped %s",
                successTestsBox.getText(), failed, skippedTestBox.getText());
    }
}
