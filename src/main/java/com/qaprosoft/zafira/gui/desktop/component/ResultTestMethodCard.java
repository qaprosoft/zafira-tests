package com.qaprosoft.zafira.gui.desktop.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ResultTestMethodCard extends AbstractUIObject {
    @FindBy(xpath = ".//div[@class='md-container md-ink-ripple']")
    private ExtendedWebElement checkbox;

    @FindBy(xpath = ".//button[contains(@class,'test-card__menu-btn')]")
    private ExtendedWebElement settingButton;

    @FindBy(xpath = "//div[@aria-hidden='false' and @class='_md md-open-menu-container md-whiteframe-z2 md-active md-clickable']//button[contains(text(),'Mark as passed')]")
    private ExtendedWebElement markAsPassedButton;

    @FindBy(xpath = "//div[@aria-hidden='false' and @class='_md md-open-menu-container md-whiteframe-z2 md-active md-clickable']//button[contains(text(),'Mark as failed')]")
    private ExtendedWebElement markAsFailedButton;

    @FindBy(xpath = "//div[@aria-hidden='false' and @class='_md md-open-menu-container md-whiteframe-z2 md-active md-clickable']//button[contains(text(),'Link issue')]")
    private ExtendedWebElement linkIssueButton;

    @FindBy(xpath = ".//div[@name='testName']//div")
    private ExtendedWebElement title;

    @FindBy(xpath = ".//span[@class='test-card__meta-item-text ng-scope']")
    private ExtendedWebElement duration;

    @FindBy(xpath = ".//span[@name='testOwner']//span")
    private ExtendedWebElement testOwner;

    @FindBy(xpath = ".//span[@name='testSessions']//span")
    private ExtendedWebElement testSessionInfoRef;

    @FindBy(xpath = ".//*[@test-label='testLabel']")
    private List<ExtendedWebElement> labels;

    @FindBy(xpath = ".//div[@class='test-card__stacktrace-title-text ng-binding']")
    private ExtendedWebElement errorStacktrace;

    public ResultTestMethodCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public boolean isErrorStacktracePresent() {
        return errorStacktrace.isPresent(2);
    }

    public boolean isCheckboxPresent() {
        return checkbox.isPresent() && checkbox.isVisible();
    }

    public boolean isMarkAsPassedButtonPresent() {
        settingButton.click();
        boolean present = markAsPassedButton.isVisible() && markAsPassedButton.isClickable();
        markAsPassedButton.click(4);
        driver.switchTo().alert().dismiss();
        return present;
    }

    public boolean isMarkAsFailedButtonPresent(){
        settingButton.click();
        boolean present = markAsFailedButton.isVisible() && markAsFailedButton.isClickable();
        markAsFailedButton.click(4);
        return present;
    }

    public boolean isLinkIssueButtonPresent(){
        return linkIssueButton.isPresent(5);
    }

    public void clickLinkIssueButton(){
        settingButton.click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(linkIssueButton.getBy()),5);
        findExtendedWebElement(linkIssueButton.getBy()).click();
    }

    public String getTitle() {
        return title.getText();
    }

    public boolean isDurationPresent() {
        return duration.isVisible();
    }

    public String getTestOwner() {
        return testOwner.getText();
    }

    public void clickTestSessionInfoRef(){
        testSessionInfoRef.click();
    }

    public String getLabelsText() {
        return labels.toString();
    }
}
