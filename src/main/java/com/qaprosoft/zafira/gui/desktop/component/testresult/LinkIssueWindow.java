package com.qaprosoft.zafira.gui.desktop.component.testresult;

import com.qaprosoft.carina.core.foundation.utils.Configuration;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.zafira.constant.WebConstant;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LinkIssueWindow extends AbstractUIObject {
    @FindBy(xpath = ".//md-icon[@aria-label='Close dialog']")
    private ExtendedWebElement closeButton;

    public LinkIssueWindow(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void closeWindow() {
        if (Configuration.get(Configuration.Parameter.BROWSER).equalsIgnoreCase("safari")
                || Configuration.getCapability("browserName").equals("safari")) {
            pause(1);
        } else {
            waitUntil(ExpectedConditions.elementToBeClickable(closeButton.getElement()), WebConstant.TIME_TO_LOAD_PAGE);
        }
        closeButton.click();
    }
}
