package com.qaprosoft.zafira.gui.component.table.row;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.zafira.service.builder.TestBuilder;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestRunTableRow extends AbstractUIObject {

    @FindBy(name = "testRunCheckbox")
    private ExtendedWebElement selectedCheckbox;

    @FindBy(css = ".test-run-card__progressbar md-progress-circular")
    private ExtendedWebElement inProgressCircularProgressBar;

    @FindBy(className = "test-run-card__title")
    private ExtendedWebElement testSuiteNameLabel;

    @FindBy(css = ".test-run-card__job-name a")
    private ExtendedWebElement jobNameLabel;

    @FindBy(className = "test-run-card__app-name")
    private ExtendedWebElement appNameLabel;

    @FindBy(xpath = ".//md-icon[text() = 'comments']")
    private ExtendedWebElement commentIcon;

    @FindBy(className = "_reviewed")
    private ExtendedWebElement reviewedIcon;

    @FindBy(css = "._env .label")
    private ExtendedWebElement environmentLabel;

    @FindBy(className = "platform-icon")
    private ExtendedWebElement browserIcon;

    @FindBy(css = "._statistics .label-success")
    private ExtendedWebElement passedLabel;

    @FindBy(css = "._statistics .label-danger, ._statistics .label-danger-empty")
    private ExtendedWebElement failedLabel;

    @FindBy(css = "._statistics .label-warning, ._statistics .label-warning-empty")
    private ExtendedWebElement skippedLabel;

    @FindBy(css = "._statistics .label-aborted-bg")
    private ExtendedWebElement abortedLabel;

    @FindBy(css = "._statistics .label-info")
    private ExtendedWebElement inProgressLabel;

    @FindBy(className = "time")
    private ExtendedWebElement startedAtLabel;

    @FindBy(css = "._menu button")
    private ExtendedWebElement menuButton;

    @FindBy(className = "test-run-card__clickable")
    private ExtendedWebElement clickableArea;

    public TestRunTableRow(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ExtendedWebElement getSelectedCheckbox() {
        return selectedCheckbox;
    }

    public void clickSelectedCheckbox() {
        selectedCheckbox.click();
    }

    public ExtendedWebElement getInProgressCircularProgressBar() {
        return inProgressCircularProgressBar;
    }

    public void clickInProgressCircularProgressBar() {
        inProgressCircularProgressBar.click();
    }

    public ExtendedWebElement getTestSuiteNameLabel() {
        return testSuiteNameLabel;
    }

    public String getTestSuiteNameLableText() {
        return testSuiteNameLabel.getText();
    }

    public ExtendedWebElement getJobNameLabel() {
        return jobNameLabel;
    }

    public String getJobNameLabelText() {
        return jobNameLabel.getText();
    }

    public ExtendedWebElement getAppNameLabel() {
        return appNameLabel;
    }

    public String getAppNameLabelText() {
        return appNameLabel.getText();
    }

    public ExtendedWebElement getCommentIcon() {
        return commentIcon;
    }

    public void clickCommentIcon() {
        commentIcon.click();
    }

    public ExtendedWebElement getReviewedIcon() {
        return reviewedIcon;
    }

    public ExtendedWebElement getEnvironmentLabel() {
        return environmentLabel;
    }

    public String getEnvironmentLabelText() {
        return environmentLabel.getText();
    }

    public ExtendedWebElement getBrowserIcon() {
        return browserIcon;
    }

    public ExtendedWebElement getPassedLabel() {
        return passedLabel;
    }

    public String getPassedLabelText() {
        return passedLabel.getText();
    }

    public ExtendedWebElement getFailedLabel() {
        return failedLabel;
    }

    public String getFailedLabelText() {
        return failedLabel.getText();
    }

    public ExtendedWebElement getSkippedLabel() {
        return skippedLabel;
    }

    public String getSkippedLabelText() {
        return skippedLabel.getText();
    }

    public ExtendedWebElement getAbortedLabel() {
        return abortedLabel;
    }

    public String getAbortedLabelText() {
        return abortedLabel.getText();
    }

    public ExtendedWebElement getInProgressLabel() {
        return inProgressLabel;
    }

    public String getInProgressLabelText() {
        return inProgressLabel.getText();
    }

    public ExtendedWebElement getStartedAtLabel() {
        return startedAtLabel;
    }

    public String getStartedAtLabelText() {
        return startedAtLabel.getText();
    }

    public ExtendedWebElement getMenuButton() {
        return menuButton;
    }

    public void clickMenuButton() {
        menuButton.click();
    }

    public ExtendedWebElement getClickableArea() {
        return clickableArea;
    }

    public void clickClickableArea() {
        clickableArea.click();
    }

    public TestBuilder.BuildStatus getStatus() {
        String[] classes = getRootElement().getAttribute("class").split(" ");
        List<String> statuses = Arrays.stream(TestBuilder.BuildStatus.values()).map(Enum::name).collect(Collectors.toList());
        String status = Arrays.stream(classes).filter(statuses::contains).findFirst().orElseThrow(() -> new RuntimeException("No test run status was detected"));
        return TestBuilder.BuildStatus.valueOf(status);
    }

}
