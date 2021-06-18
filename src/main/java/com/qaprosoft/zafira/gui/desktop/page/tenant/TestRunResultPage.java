package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.common.NavigationMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.TenantHeader;
import com.qaprosoft.zafira.gui.desktop.component.testresult.LinkIssueWindow;
import com.qaprosoft.zafira.gui.desktop.component.testresult.ResultSessionWindow;
import com.qaprosoft.zafira.gui.desktop.component.testresult.ResultTestMethodCard;
import com.qaprosoft.zafira.gui.desktop.component.testresult.RunResultDetailsBar;
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

    @FindBy(xpath = "//test-card[@test='testItem']")
    private List<ResultTestMethodCard> testMethods;

    @FindBy(xpath = "//*[@class='md-sidenav-right test-sessions-sidenav ng-isolate-scope _md md-whiteframe-1dp']")
    private ResultSessionWindow resultSessionWindow;

    @FindBy(xpath = "//md-dialog[@class='issue-modal styled-modal _md flex-50 background-clear-white md-transition-in']")
    private LinkIssueWindow linkIssueWindow;

    @FindBy(xpath = "//a[contains(@class,'back_button')]//md-icon")
    private ExtendedWebElement backIcon;

    @FindBy(xpath = "//div[@id='pageTitle']//span[text()='Test results']")
    private ExtendedWebElement pageTitle;

    @FindBy(xpath = "//div[@class='test-run-group-row test-details__header-actions _default']")
    private RunResultDetailsBar resultBar;

    @FindBy(xpath = "//*[text()='Expand all labels']/parent::div")
    private ExtendedWebElement expandAllLabel;

    @FindBy(xpath = "//*[text()='Collapse all labels']/parent::div")
    private ExtendedWebElement collapseAllLabel;

    @FindBy(xpath = "//div[@class='test-details__table-col ng-binding']")
    private ExtendedWebElement noTestsMessage;

    public TestRunResultPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(pageTitle);
    }

    public String getPageTitle() {
        return pageTitle.getText();
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
                .trim().replaceAll(" +", " ").split(" ");
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
        card.clickLinkIssueButton();
        return linkIssueWindow;
    }

}
