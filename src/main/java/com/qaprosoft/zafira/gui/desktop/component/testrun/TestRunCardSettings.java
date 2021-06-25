package com.qaprosoft.zafira.gui.desktop.component.testrun;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//div[contains(@class,'md-open-menu-container md-whiteframe-z2 md-active md-clickable')]
public class TestRunCardSettings extends AbstractUIObject {
    @FindBy(xpath = "//form[@name='comments_form']")
    private ReviewForm reviewForm;

    @FindBy(xpath = "//md-dialog[@aria-label='Email']")
    private EmailForm emailForm;

    @FindBy(xpath = "//md-dialog[@aria-label='Rebuild testrun']")
    private RerunForm rerunForm;

    @FindBy(xpath = "//form[@name='build_now_form']")
    private BuildNowForm buildNowForm;

    @FindBy(xpath = ".//button[@name='open']")
    private ExtendedWebElement openInANewTab;

    @FindBy(xpath = ".//button[@name='copyLink']")
    private ExtendedWebElement copyLink;

    @FindBy(xpath = ".//button[@name='markAsReviewed']")
    private ExtendedWebElement markAsReviewed;

    @FindBy(xpath = ".//button[@name='sendAsEmail']")
    private ExtendedWebElement sendAsEmail;

    @FindBy(xpath = ".//button[@name='export']")
    private ExtendedWebElement exportToHtml;

    @FindBy(xpath = ".//button[@name='buildNow']")
    private ExtendedWebElement buildNow;

    @FindBy(xpath = ".//button[@name='rebuild']")
    private ExtendedWebElement rerun;

    @FindBy(xpath = ".//button[@name='delete']")
    private ExtendedWebElement delete;

    public TestRunCardSettings(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
