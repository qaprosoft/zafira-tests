package com.qaprosoft.zafira.gui.desktop.component.testresult;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class LinkIssueWindow extends AbstractUIObject {
    @FindBy(xpath = "//md-icon[@aria-label='Close dialog']")
    private ExtendedWebElement closeButton;

    public LinkIssueWindow(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void closeWindow() {
        closeButton.click();
    }
}
