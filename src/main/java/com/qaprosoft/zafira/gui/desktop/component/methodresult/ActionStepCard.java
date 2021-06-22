package com.qaprosoft.zafira.gui.desktop.component.methodresult;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//div[@class='testrun-info__tab-table-row ng-scope']
public class ActionStepCard extends AbstractUIObject {
    @FindBy(xpath = ".//div[@class='testrun-info__tab-table-col _visuals']")
    private ExtendedWebElement visualMessage;

    @FindBy(xpath = ".//div[@class='testrun-info__tab-table-col _start']")
    private ExtendedWebElement startMessage;

    @FindBy(xpath = ".//div[@class='testrun-info__tab-table-col _status']")
    private ExtendedWebElement statusMessage;

    @FindBy(xpath = ".//div[@name='logMessage']")
    private ExtendedWebElement logMessage;

    public ActionStepCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
