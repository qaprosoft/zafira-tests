package com.qaprosoft.zafira.gui.desktop.component.testrun;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//md-dialog[@aria-label='Rebuild testrun']
public class RerunForm extends AbstractUIObject {
    @FindBy(id = "modalTitle")
    private ExtendedWebElement modalTitle;

    @FindBy(xpath = "//md-icon[@aria-label='Close dialog']")
    private ExtendedWebElement closeDialog;

    @FindBy(id = "onlyFailures")
    private ExtendedWebElement onlyFailuresRadio;

    @FindBy(id = "allTests")
    private ExtendedWebElement allTests;

    @FindBy(id = "rerun")
    private ExtendedWebElement rerunButton;

    public RerunForm(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
