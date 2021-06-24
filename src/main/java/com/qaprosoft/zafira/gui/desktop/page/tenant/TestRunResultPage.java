package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.utils.Configuration;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.common.HelpMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.NavigationMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.TenantHeader;
import com.qaprosoft.zafira.gui.desktop.component.testresult.*;
import com.qaprosoft.zafira.gui.desktop.component.testrun.TestRunCard;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TestRunResultPage extends AbstractPage {
    @FindBy(id = "nav-container")
    private NavigationMenu navigationMenu;

    @FindBy(id = "header")
    private TenantHeader header;

    @FindBy(xpath = "//div[contains(@class,'test-run-card ng-isolate-scope _single')]")
    private TestRunCard testCard;

    @FindBy(xpath = "//div[@class='test-run-group-row test-details__header-actions _default']")
    private RunResultDetailsBar resultBar;

    @FindBy(xpath = "//div[contains(@class,'test-details__header-actions _bulk')]")
    private RunResultActionBar actionBar;

    @FindBy(xpath = "//test-card[@test='testItem']")
    private List<ResultTestMethodCard> testMethods;

    @FindBy(xpath = "//*[@class='md-sidenav-right test-sessions-sidenav ng-isolate-scope _md md-whiteframe-1dp']")
    private ResultSessionWindow resultSessionWindow;

    @FindBy(xpath = "//md-dialog[contains(@class,'issue-modal')]")
    private LinkIssueWindow linkIssueWindow;

    @FindBy(xpath = "//div[@data-embed='helpCenterForm']")
    private HelpMenu helpMenu;

    @FindBy(xpath = "//button[@aria-label='Help']")
    private ExtendedWebElement helpButton;

    @FindBy(xpath = "//a[contains(@class,'back_button')]//md-icon")
    private ExtendedWebElement backIcon;

    @FindBy(xpath = "//div[@id='pageTitle']//span[text()='Test results']")
    private ExtendedWebElement pageTitle;

    @FindBy(xpath = "//*[text()='Expand all labels']/parent::div")
    private ExtendedWebElement expandAllLabel;

    @FindBy(xpath = "//*[text()='Collapse all labels']/parent::div")
    private ExtendedWebElement collapseAllLabel;

    @FindBy(xpath = "//div[@class='test-details__table-col ng-binding']")
    private ExtendedWebElement noTestsMessage;

    @FindBy(xpath = "//div[contains(@class,'md-open-menu-container md-whiteframe-z2 md-active')]//button[contains(text(),'Link issue')]")
    private ExtendedWebElement linkIssueButton;

    @FindBy(xpath = "//span[@class='tab-additional__table-data _label' and text()='Status']")
    private ExtendedWebElement statusLabel;

    @FindBy(xpath = "//span[@class='tab-additional__table-data _label' and text()='Owner']")
    private ExtendedWebElement ownerLabel;

    @FindBy(xpath = "//span[@class='tab-additional__table-data _label' and text()='Started']")
    private ExtendedWebElement startedLabel;

    @FindBy(xpath = "//span[@class='tab-additional__table-data _label' and text()='Duration']")
    private ExtendedWebElement durationLabel;

    @FindBy(xpath = "//button//span[contains(@class,'button-text')]")
    private ExtendedWebElement statusButton;

    @FindBy(xpath = "//md-menu-content[@class='tab-additional__menu-content']//span[contains(text(),'passed')]/ancestor::button")
    private ExtendedWebElement passedButton;

    @FindBy(xpath = "//md-menu-content[@class='tab-additional__menu-content']//span[contains(text(),'failed')]/ancestor::button")
    private ExtendedWebElement failedButton;

    @FindBy(xpath = "//table[@class='tab-additional__table']//td[@class='tab-additional__table-data _test-owner-data']/span")
    private ExtendedWebElement ownerTitle;

    @FindBy(xpath = "//table[@class='tab-additional__table']//td[@class='tab-additional__table-data _started-data']/span")
    private ExtendedWebElement startedTitle;

    @FindBy(xpath = "//table[@class='tab-additional__table']//duration")
    private ExtendedWebElement durationTitle;

    public TestRunResultPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(pageTitle);
    }

    public String getPageTitle() {
        return pageTitle.getText().trim();
    }

    public boolean isBackButtonActive() {
        return backIcon.isClickable() && backIcon.isVisible();
    }

    public TestRunCard getPageCard() {
        return testCard;
    }

    public RunResultDetailsBar getResultBar() {
        return resultBar;
    }

    public boolean isCollapseAllLabelVisible() {
        return collapseAllLabel.isVisible();
    }

    public boolean isExpandAllLabelVisible() {
        return expandAllLabel.isVisible();
    }

    public boolean isNumberOfTestsAsExpected() {
        return testMethods.size() == getNumberOfMethods();
    }

    public boolean isNumberOfTestsAsExpected(int expected) {
        return (testMethods.size() == getNumberOfMethods()) && (testMethods.size() == expected);
    }

    private int getNumberOfMethods() {
        String[] numbers = testCard.getRunResult().replaceAll("[^\\d\\s]", "")
                .replaceAll(" +", " ").trim().split(" ");
        int totalTests = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (i != 2) {
                totalTests += Integer.parseInt(numbers[i]);
            }
        }
        return totalTests;
    }

    public List<ResultTestMethodCard> getTestMethods() {
        return testMethods;
    }

    public boolean isAllFailedTestsHaveErrorTrace() {
        resultBar.clickFailedButton();
        if (noTestsMessage.isVisible(2)) {
            resultBar.clickResetButton();
            return true;
        }
        for (ResultTestMethodCard testMethodCard : testMethods) {
            if (!testMethodCard.isErrorStacktracePresent()) {
                resultBar.clickResetButton();
                return false;
            }
        }
        resultBar.clickResetButton();
        return true;
    }

    public boolean isAllPassedTestsHaveNoErrorTrace() {
        resultBar.clickPassedButton();
        if (noTestsMessage.isVisible(2)) {
            resultBar.clickResetButton();
            return true;
        }
        for (ResultTestMethodCard testMethodCard : testMethods) {
            if (testMethodCard.isErrorStacktracePresent()) {
                resultBar.clickResetButton();
                return false;
            }
        }
        resultBar.clickResetButton();
        return true;
    }

    public TenantHeader getHeader() {
        return header;
    }

    public ResultSessionWindow openResultSessionWindow(ResultTestMethodCard card) {
        card.clickTestSessionInfoRef();
        return resultSessionWindow;
    }

    public LinkIssueWindow openLinkIssueWindow(ResultTestMethodCard card) {
        if (Configuration.getCapability("browserName").equals("safari")){
            card.clickSettings();
            pause(1);
            driver.findElement(linkIssueButton.getBy()).click();
        } else {
            card.clickSettings();
            findExtendedWebElement(linkIssueButton.getBy()).click();
        }
        return linkIssueWindow;
    }

}
