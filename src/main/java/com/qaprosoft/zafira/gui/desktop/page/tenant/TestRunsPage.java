package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.common.NavigationMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.TenantHeader;
import com.qaprosoft.zafira.gui.desktop.component.testrun.TestRunCard;
import com.qaprosoft.zafira.gui.desktop.component.testrun.TestRunLaunchCard;
import com.qaprosoft.zafira.gui.desktop.component.testrun.TestRunsLauncher;
import com.qaprosoft.zafira.util.WaitUtil;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.Assert;

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

    @FindBy(xpath = "//input[contains(@class,'runs-filter')]")
    private ExtendedWebElement searchField;

    @FindBy(xpath = "//md-select[@name='browser']")
    private ExtendedWebElement browserFilterButton;

    @FindBy(xpath = "//md-select[@name='platform']")
    private ExtendedWebElement platformFilterButton;

    @FindBy(xpath = "//button[contains(@class,'more-button')]")
    private ExtendedWebElement filterMoreButton;

    @FindBy(xpath = "//a[text()='Show all saved filters']")
    private ExtendedWebElement showAllSavedFiltersButton;

    @FindBy(xpath = "//div[@class='label ng-binding']")
    private ExtendedWebElement pagination;

    public TestRunsPage(WebDriver driver) {
        super(driver);
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
            if (testRunCards.size() > 1) {
                bulkCheckBox.click();
            }
            bulkDeleteButton.click();
            try {
                driver.switchTo().alert().accept();
            } catch (NoAlertPresentException ex) {
                ex.printStackTrace();
            }
        }
    }

    public TestRunCard getTestRunCard(String suiteName) {
        return getTestRunCard(suiteName, 60);
    }

    public TestRunCard getTestRunCard(String suiteName, long testRunWaitTime) {
        Clock clock = Clock.systemDefaultZone();
        Instant end = clock.instant().plusSeconds(testRunWaitTime);
        Sleeper sleeper = Sleeper.SYSTEM_SLEEPER;
        Duration interval = Duration.ofSeconds(testRunWaitTime / 4);
        while (end.isAfter(clock.instant())) {
            for (TestRunCard card : testRunCards) {
                if (card.getTitle().toLowerCase().contains(suiteName.toLowerCase()) && card.isTestComplete()) {
                    return card;
                }
            }
            try {
                sleeper.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Assert.fail("Can't find test run with name: " + suiteName);
        return null;
    }

    public TestRunResultPage toTestRunResultPage(String testRunName) {
        for (TestRunCard card : testRunCards) {
            if (card.getTitle().toLowerCase().contains(testRunName.toLowerCase()) && card.isTestComplete()) {
                return card.toTestRunResultPage();
            }
        }
        Assert.fail("Can't find such test run");
        return null;
    }

    public boolean isSearchFieldPresent() {
        return searchField.isPresent();
    }

    public boolean isShowAllSavedFiltersButtonPresent() {
        return showAllSavedFiltersButton.isVisible() && showAllSavedFiltersButton.isClickable();
    }

    public boolean isFilterMoreButtonPresent() {
        return filterMoreButton.isVisible() && filterMoreButton.isClickable();
    }

    public boolean isBrowserFilterButtonPresent() {
        return browserFilterButton.isVisible() && browserFilterButton.isClickable();
    }

    public boolean isPlatformFilterButtonPresent() {
        return platformFilterButton.isVisible() && platformFilterButton.isClickable();
    }

    public String getNumberOfTestRunCards() {
        return String.valueOf(testRunCards.size());
    }

    public String getNumberOfTestsOnThePage() {
        String[] arr = pagination.getText().split(" - | of ");
        return arr[1];
    }
}
