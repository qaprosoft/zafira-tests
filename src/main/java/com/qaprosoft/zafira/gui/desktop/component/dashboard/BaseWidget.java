package com.qaprosoft.zafira.gui.desktop.component.dashboard;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//div[@ng-attr-id='widget-{{widget.id}}']
public abstract class BaseWidget extends AbstractUIObject {
    @FindBy(xpath = ".//div[@class='panel-heading ng-binding']")
    private ExtendedWebElement widgetTitle;

    @FindBy(xpath = ".//button[@aria-label='Widget settings']")
    private ExtendedWebElement widgetSettings;

    @FindBy(xpath = "//div[contains(@class,'md-active md-clickable')]//button[@name='sendEmail']")
    private ExtendedWebElement sendByEmailButton;

    public BaseWidget(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
