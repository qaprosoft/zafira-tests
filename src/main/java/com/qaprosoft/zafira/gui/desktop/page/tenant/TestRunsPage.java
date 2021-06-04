package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.Launcher;
import com.qaprosoft.zafira.gui.desktop.component.NavigationMenu;
import com.qaprosoft.zafira.gui.desktop.component.TestRunCard;
import com.qaprosoft.zafira.gui.desktop.component.TestRunLaunchCard;
import com.qaprosoft.zafira.util.WaitUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TestRunsPage extends AbstractPage {
    @FindBy(id = "nav")
    private NavigationMenu navigationMenu;

    @FindBy(xpath = "//div[@class='test-run-card ng-scope ng-isolate-scope _launch']")
    private List<TestRunLaunchCard> testRunLaunchCards;

    @FindBy(xpath = "//div[@class='test-run-card ng-scope ng-isolate-scope']")
    private List<TestRunCard> testRunCards;

    @FindBy(xpath = "//div[@class='fixed-page-header-container']//div[contains(text(),'Test runs')]")
    private ExtendedWebElement sectionHeader;

    @FindBy(xpath = "//span[text()='Launcher']")
    private ExtendedWebElement launcherButton;

    @FindBy(className = "md-dialog-content")
    private Launcher launcher;

    @FindBy(xpath = "//div[contains(@class,'test-runs__bulk')]//button[@id='bulkDeleteTestRuns']")
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

    public Launcher openLauncherWindow(){
        launcherButton.click();
        return launcher;
    }

    public boolean isTestLaunched(String testName) {
        Boolean loaded = WaitUtil.waitListToLoad(testRunLaunchCards, 5000, 500);
        if (loaded) {
            for (TestRunLaunchCard testRunCard : testRunLaunchCards) {
                if (testRunCard.getTitle().contains(testName)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void deleteAllTestRunCards(){
        if (!testRunCards.isEmpty()){
            testRunCards.get(0).clickCheckBox();
            bulkCheckBox.click();
            bulkDeleteButton.click();
            driver.switchTo().alert().accept();
        }
    }
}
