package com.qaprosoft.zafira.gui.desktop.component.testresult;

import com.qaprosoft.carina.core.foundation.utils.Configuration;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.zafira.constant.WebConstant;
import com.qaprosoft.zafira.util.WaitUtil;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

//test-card[@test='testItem']
public class ResultTestMethodCard extends AbstractUIObject {
    @FindBy(xpath = ".//div[@class='md-container md-ink-ripple']")
    private ExtendedWebElement checkbox;

    @FindBy(xpath = ".//button[contains(@class,'test-card__menu-btn')]")
    private ExtendedWebElement settingsButton;

    @FindBy(xpath = "//div[contains(@class,'md-open-menu-container md-whiteframe-z2 md-active md-clickable')]//button[contains(text(),'Mark as passed')]")
    private ExtendedWebElement markAsPassedButton;

    @FindBy(xpath = "//div[contains(@class,'md-open-menu-container md-whiteframe-z2 md-active md-clickable')]//button[contains(text(),'Mark as failed')]")
    private ExtendedWebElement markAsFailedButton;

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

    @FindBy(xpath = ".//div[@class='test-card__stacktrace _failed _isBorder']//div[@class='test-card__stacktrace-title-text ng-binding']")
    private ExtendedWebElement errorStacktracePreview;

    @FindBy(xpath = ".//div[@class='test-card__stacktrace-message ng-binding ng-scope']")
    private ExtendedWebElement errorStacktraceFull;

    public ResultTestMethodCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public boolean isErrorStacktracePresent() {
        return errorStacktracePreview.isVisible(WebConstant.TIME_TO_LOAD_PAGE);
    }

    public boolean isCheckboxPresent() {
        return checkbox.isPresent(WebConstant.TIME_TO_LOAD_PAGE) && checkbox.isVisible(WebConstant.TIME_TO_LOAD_PAGE);
    }

    public boolean isMarkAsPassedButtonPresent() {
        clickSettings();
        boolean present = markAsPassedButton.isVisible(WebConstant.TIME_TO_LOAD_PAGE) && markAsPassedButton.isClickable(WebConstant.TIME_TO_LOAD_PAGE);
        markAsPassedButton.click(WebConstant.TIME_TO_LOAD_PAGE);
        driver.switchTo().alert().dismiss();
        return present;
    }

    public boolean isMarkAsFailedButtonPresent() {
        clickSettings();
        boolean present = markAsFailedButton.isVisible() && markAsFailedButton.isClickable();
        markAsFailedButton.click(WebConstant.TIME_TO_LOAD_PAGE);
        driver.switchTo().alert().dismiss();
        return present;
    }

    public void clickSettings() {
        if (Configuration.get(Configuration.Parameter.BROWSER).equalsIgnoreCase("safari")
                || Configuration.getCapability("browserName").equals("safari")) {
            pause(1);
        }
        settingsButton.click();
    }

    public String getTitle() {
        return title.getText().trim();
    }

    public boolean isDurationPresent() {
        return duration.isVisible();
    }

    public String getTestOwner() {
        return testOwner.getText().trim();
    }

    public void clickTestSessionInfoRef() {
        if (Configuration.get(Configuration.Parameter.BROWSER).equalsIgnoreCase("safari")
                || Configuration.getCapability("browserName").equals("safari")) {
            pause(1);
        } else {
            waitUntil(ExpectedConditions.visibilityOf(testSessionInfoRef.getElement()), 3);
        }
        testSessionInfoRef.click();
    }

    public String getLabelsText() {
        StringBuilder labelsStr = new StringBuilder();
        WaitUtil.waitListToLoad(labels, 400, 100);
        for (ExtendedWebElement el : labels) {
            labelsStr.append(el.getText().trim()).append(" ");
        }
        return labelsStr.toString().replaceAll("\n", " ").replaceAll(" +", " ").trim();
    }
}
