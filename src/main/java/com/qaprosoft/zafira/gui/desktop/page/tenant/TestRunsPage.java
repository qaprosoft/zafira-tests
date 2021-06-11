package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.*;
import com.qaprosoft.zafira.util.WaitUtil;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class TestRunsPage extends AbstractPage {
    @FindBy(id = "nav-container")
    private NavigationMenu navigationMenu;

    @FindBy(id = "header")
    private TenantHeader header;

    @FindBy(xpath = "//div[@class='test-run-card ng-scope ng-isolate-scope _launch']")
    private List<TestRunLaunchCard> testRunLaunchCards;

    @FindBy(xpath = "//div[@class='test-run-card ng-scope ng-isolate-scope']")
    private List<TestRunCard> testRunCards;

    @FindBy(className = "md-dialog-content")
    private TestRunsLauncher testRunsLauncher;

    @FindBy(id = "noDataYet")
    private ExtendedWebElement noDataField;

    @FindBy(xpath = "//div[@class='fixed-page-header-container']//div[contains(text(),'Test runs')]")
    private ExtendedWebElement sectionHeader;

    @FindBy(xpath = "//span[text()='Launcher']")
    private ExtendedWebElement launcherButton;

    @FindBy(xpath = "//button[@id='bulkDeleteTestRuns']")
    private ExtendedWebElement bulkDeleteButton;

    @FindBy(xpath = "//div[contains(@class,'test-runs__bulk')]//div[@class='md-container md-ink-ripple']")
    private ExtendedWebElement bulkCheckBox;

    public TestRunsPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(sectionHeader);
    }

    public NavigationMenu getNavigationMenu() {
        return navigationMenu;
    }

    public TestRunsLauncher openLauncherWindow() {
        launcherButton.click();
        return testRunsLauncher;
    }

    public boolean isTestLaunched(String testName) {
        Boolean loaded = WaitUtil.waitListToLoad(testRunLaunchCards, 5000, 500);
        if (loaded) {
            for (TestRunLaunchCard testRunCard : testRunLaunchCards) {
                if (testRunCard.getTitle().toLowerCase().contains(testName.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void deleteAllTestRunCards() {
        while (!noDataField.isVisible(1)) {
            testRunCards.get(0).clickCheckBox();
            bulkCheckBox.click();
            if (waitUntil(ExpectedConditions.visibilityOf(bulkDeleteButton.getElement()), 3)) {
                bulkDeleteButton.click();
                try {
                    driver.switchTo().alert().accept();
                } catch (NoAlertPresentException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public String getTestRunResult(String suiteName) {
        return getTestRunResult(suiteName, 60);
    }

    public String getTestRunResult(String suiteName, long testRunWaitTime) {
        Clock clock = Clock.systemDefaultZone();
        Instant end = clock.instant().plusSeconds(testRunWaitTime);
        Sleeper sleeper = Sleeper.SYSTEM_SLEEPER;
        Duration interval = Duration.ofSeconds(testRunWaitTime / 4);
        while (end.isAfter(clock.instant())) {
            for (TestRunCard card : testRunCards) {
                if (card.getTitle().toLowerCase().contains(suiteName.toLowerCase()) && card.isTestComplete()) {
                    return card.getRunResult();
                }
            }
            try {
                sleeper.sleep(interval);
                this.refresh();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "no result";
    }

    public TestRunResultPage toTestRunResultPage(String testRunName) {
        for (TestRunCard card : testRunCards) {
            if (card.getTitle().toLowerCase().contains(testRunName.toLowerCase()) && card.isTestComplete()) {
                return card.toTestRunResultPage();
            }
        }

        throw new RuntimeException("Can't find such test run");
    }
}
