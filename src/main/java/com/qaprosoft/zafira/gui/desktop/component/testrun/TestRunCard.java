package com.qaprosoft.zafira.gui.desktop.component.testrun;

import com.qaprosoft.carina.core.foundation.utils.Configuration;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunResultPage;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TestRunCard extends TestRunCardBase {

    @FindBy(xpath = ".//div[@class='test-run-card__cell _platform']")
    private ExtendedWebElement platform;

    @FindBy(xpath = ".//span[contains(@class,'label-success')]")
    private ExtendedWebElement successTestsBox;

    @FindBy(xpath = ".//span[contains(@title,'Failed')]")
    private ExtendedWebElement failedTestsBox;

    @FindBy(xpath = ".//span[contains(@title,'Skipped')]")
    private ExtendedWebElement skippedTestBox;

    @FindBy(xpath = ".//div[@class='test-run-card__time light_text ng-scope']")
    private ExtendedWebElement testDuration;

    @FindBy(xpath = ".//a[@class='test-run-card__clickable ng-scope']")
    private ExtendedWebElement resultReference;

    @FindBy(xpath = ".//span[@class='platform-icon chrome']")
    private ExtendedWebElement chromeIcon;

    @FindBy(xpath = ".//span[@class='label label-default ng-binding ng-scope']")
    private ExtendedWebElement browserVersion;

    @FindBy(xpath = ".//span[@class='platform-icon api']")
    private ExtendedWebElement apiIcon;

    @FindBy(xpath = ".//div[@class='test-run-card__job-name ng-scope']")
    private ExtendedWebElement zebrunnerJobName;

    @FindBy(xpath = ".//div[@class='time']")
    private ExtendedWebElement startedText;

    @FindBy(xpath = ".//button[@name='testRunSetting']")
    private ExtendedWebElement testSettings;

    @FindBy(xpath = ".//div[@class='test-run-card__title']//button[@title='Copy to clipboard']")
    private ExtendedWebElement copyTestRunNameButton;

    public TestRunCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public boolean isTestComplete() {
        return testDuration.isPresent() && testDuration.isVisible();
    }

    public String getRunResult() {
        String failed = failedTestsBox.getText().trim().replace('\n', ' ');
        if (Configuration.get(Configuration.Parameter.BROWSER).equalsIgnoreCase("safari") || Configuration.getCapability("browserName").equals("safari")) {
            failed = failedTestsBox.getText().trim().replace("|", " | ");
        }
        return String.format("Passed %s, Failure %s, Skipped %s",
                successTestsBox.getText().trim(), failed, skippedTestBox.getText());
    }

    public TestRunResultPage toTestRunResultPage() {
        resultReference.click();
        return new TestRunResultPage(driver);
    }

    public boolean isWebChromeTest() {
        return chromeIcon.isVisible() && browserVersion.isVisible();
    }

    public boolean isApiTest() {
        return apiIcon.isVisible();
    }

    public String getJobName() {
        return zebrunnerJobName.getText().trim();
    }

    public String getHowLongAgoStarted() {
        return startedText.getText().trim();
    }

    public boolean isTestSettingsButtonPresent() {
        return testSettings.isVisible() && testSettings.isClickable();
    }

    public boolean isCopyTestNameButtonActive() {
        title.hover();
        return copyTestRunNameButton.isClickable() && copyTestRunNameButton.isVisible();
    }
}
